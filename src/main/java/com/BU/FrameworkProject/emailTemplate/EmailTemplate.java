package com.BU.FrameworkProject.emailTemplate;

public enum EmailTemplate {
    demoTemplate("C:\\Users\\balavikneshram.j\\Downloads\\FrameworkProject\\FrameworkProject\\src\\main\\java\\com\\BU\\FrameworkProject\\emailTemplate\\Demo.txt");

    private final String filePath;

    EmailTemplate(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath(){
      return filePath;
   }
}
