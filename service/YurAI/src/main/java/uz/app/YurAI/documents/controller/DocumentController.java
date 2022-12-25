package uz.app.YurAI.documents.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import uz.app.YurAI.common.model.StdResponse;
import uz.app.YurAI.documents.model.AIRequest;
import uz.app.YurAI.documents.model.AIResponse;
import uz.app.YurAI.documents.model.Document;
import uz.app.YurAI.documents.service.DocumentService;

@RestController
@RequestMapping(value = "/document")
public class DocumentController {
    @Autowired
    public DocumentService documentService;

    private static String imageToBase64(byte[] imageBytes) {
        if(imageBytes == null)
            return "";
        Encoder base64encoder = java.util.Base64.getEncoder();
        return base64encoder.encodeToString(imageBytes);
    }

    @PostMapping(value="/process", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public AIResponse processDocument(@RequestParam("file") MultipartFile file) {
        byte[] imageBytes = null;
        
        try {
            imageBytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Document doc = new Document(file.getOriginalFilename(), imageToBase64(imageBytes));
        documentService.saveDocument(doc);
        AIResponse aiOrderResponse = documentService.sendToAI(doc.getSource(), "");

        return aiOrderResponse;
    }

    @PostMapping(value="/process/test", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AIResponse processDocumentTest(@RequestBody AIRequest request){
        System.out.println(request.toString());
        return new AIResponse("AI-scanned text");
    }

    @GetMapping(value="/getall")
    public Document[] getAllDocuments() {
        return documentService.getAllDocuments();
    }
}
