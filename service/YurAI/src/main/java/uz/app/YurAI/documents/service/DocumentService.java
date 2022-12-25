package uz.app.YurAI.documents.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import ch.qos.logback.core.subst.Token;
import uz.app.Anno.orm.*;
import uz.app.Anno.orm.exceptions.AnnoException;
import uz.app.Anno.orm.exceptions.ValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import uz.app.YurAI.common.model.StdResponse;
import uz.app.YurAI.documents.model.AIRequest;
import uz.app.YurAI.documents.model.AIResponse;
import uz.app.YurAI.documents.model.Document;
import uz.app.YurAI.security.JwtTokenUtil;
import uz.app.YurAI.security.model.SecurityUser;

@Component
public class DocumentService {
    private final String AI_SERVICE_URL = "localhost";
    private final String AI_SERVICE_PORT = "8000";
    private final String AI_SERVICE_PROCESS_PATH = "/";
    Repository<Document> docRepo;
    Repository<AIRequest> aiReqRepo;
    
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

    public DocumentService() {
        try {
            RepositoryFactory.setContext(new OrmContext("db.properties"));
            docRepo = RepositoryFactory.getRepository(Document.class);
            aiReqRepo = RepositoryFactory.getRepository(AIRequest.class);
        } catch (SQLException | AnnoException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveDocument(Document doc) {
        try {
            docRepo.save(doc);
        } catch (SQLException | ValidationException | AnnoException e) {
            throw new RuntimeException(e);
        }
    }

    public Document getDocumentById(long id) {
        Document result = null;
        try {
            result = docRepo.getById(id);
        } catch (SQLException | AnnoException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private String getInputStreamContent(InputStream inStream) throws IOException {
        if(inStream == null)
            return null;
        BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    public AIResponse sendToAI(String base64img, String contentType) {
        Gson gson = new Gson();
        AIRequest aiRequest = new AIRequest(base64img);
        // ***
        SecurityUser user = (SecurityUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = jwtTokenUtil.generateToken(user);
        // ***
        try {
            aiReqRepo.save(aiRequest);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        try {
            URL url = new URL("http://"+AI_SERVICE_URL+":"+AI_SERVICE_PORT+AI_SERVICE_PROCESS_PATH);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Authorization", "Bearer " + token);
            
            OutputStream out = con.getOutputStream();
            out.write(gson.toJson(aiRequest).getBytes());
            out.flush();
            out.close();
            // ***************
            int status = con.getResponseCode();
            if (status > 299) {
                String errJson = getInputStreamContent(con.getErrorStream());
                System.out.println(errJson);
                Map<String, Object> err = gson.fromJson(errJson, Map.class);
                AIResponse response = new AIResponse();
                response.setCode(err.getOrDefault("code", "-100").toString());
                response.setMsg(err.getOrDefault("msg", "Error - " + status).toString());
                return response;
            } else {
                String responseJson = getInputStreamContent(con.getInputStream());
                System.out.println(responseJson);
                AIResponse response = gson.fromJson(responseJson, AIResponse.class);
                response.setCode("0");
                response.setMsg("success");
                return response;
            }
        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Document[] getAllDocuments() {
        try {
            Document[] allDocs = docRepo.getAll();
            for (Document document : allDocs) {
                document.setSource(null);
            }
            return allDocs;
        } catch(Throwable ex) {
            throw new RuntimeException(ex);
        }
    }
}
