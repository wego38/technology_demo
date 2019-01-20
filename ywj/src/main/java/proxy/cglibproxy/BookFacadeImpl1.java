package proxy.cglibproxy;

import lombok.Data;
import lombok.ToString;

@Data
public class BookFacadeImpl1 {

    public void addBook() {  
        System.out.println("增加图书的普通方法...");  
    }


    public String toString(){
        return "ywj";
    }
}  