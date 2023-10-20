public class country_name {
    private String name;        // 국가 이름을 나타내는 문자열
    private int flagResource;   // 국기 이미지 리소스의 ID

    public country_name(String name, int flagResource) {
        this.name = name;
        this.flagResource = flagResource;
    }

    public String getName() {
        return name;           // 국가 이름을 반환
    }

    public int getFlagResource() {
        return flagResource;   // 국기 이미지 리소스의 ID를 반환
    }
}