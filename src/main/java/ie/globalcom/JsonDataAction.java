package ie.globalcom;

import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;

public class JsonDataAction extends ActionSupport {

    private String keyword;
    private String status;

    public String execute(){
        if (keyword.equals("")){
            this.status = "keyword should not be null";
        } else{
            try {
                this.status = DatabaseService.getToggleClass(keyword) ? "success" : "failed: check input";
            } catch (SQLException e) {
                status = "failed: SQL Exception";
                e.printStackTrace();
            }
        }
        return SUCCESS;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
