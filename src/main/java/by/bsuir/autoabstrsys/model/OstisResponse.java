package by.bsuir.autoabstrsys.model;

import java.util.List;

public class OstisResponse {
    private List<Object[]> sys;
    private List<Object[]> main;
    private List<Object[]> common;

    public List<Object[]> getSys() {
        return sys;
    }

    public void setSys(List<Object[]> sys) {
        this.sys = sys;
    }

    public List<Object[]> getMain() {
        return main;
    }

    public void setMain(List<Object[]> main) {
        this.main = main;
    }

    public List<Object[]> getCommon() {
        return common;
    }

    public void setCommon(List<Object[]> common) {
        this.common = common;
    }
}
