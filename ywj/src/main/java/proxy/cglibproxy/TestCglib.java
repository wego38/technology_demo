package proxy.cglibproxy;
public class TestCglib {  
    
    public static void main(String[] args) {  
        BookFacadeCglib cglib=new BookFacadeCglib();
        BookFacadeImpl1 bookFacadeImpl1 = new BookFacadeImpl1();
        BookFacadeImpl1 bookCglib=(BookFacadeImpl1)cglib.getInstance(bookFacadeImpl1);
        bookCglib.addBook();  
    }  
} 