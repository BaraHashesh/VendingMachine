package display;

public class SnackVendingMachineDisplay extends Display {

    private String content;

    public SnackVendingMachineDisplay() {
        this.content = "";
    }

    public String getDisplayContent() {
        return content;
    }

    public void setDisplayContent(String newContent) {
        this.content = newContent;
    }

    public void appendContent(String newContent) {
        this.content += newContent;
    }

    @Override
    public void printDisplay() {
        System.out.println(this.content);
    }
}
