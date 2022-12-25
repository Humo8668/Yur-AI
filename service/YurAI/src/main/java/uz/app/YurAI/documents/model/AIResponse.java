package uz.app.YurAI.documents.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import uz.app.YurAI.common.model.StdResponse;

public class AIResponse extends StdResponse{
    String text;
    Collection<HashMap<String, Object>> index = new ArrayList<>();


    public AIResponse() {}

    public AIResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    
    public Collection<HashMap<String, Object>> getIndex() {
        return index;
    }

    public void setIndex(Collection<HashMap<String, Object>> index) {
        this.index = index;
    }
}
