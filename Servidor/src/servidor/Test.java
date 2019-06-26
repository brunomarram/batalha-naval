package servidor;

public class Test {
    public static void main(String[] args) {
        Servidor server = new Servidor();
        server.call("start", "2,20");
        server.call("play", "B,2");
    }
}
