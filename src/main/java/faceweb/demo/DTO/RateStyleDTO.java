package faceweb.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RateStyleDTO {
    private long styleId0;
    private int rate0;
    private long styleId1;
    private int rate1;
    private long styleId2;
    private int rate2;
    private long styleId3;
    private int rate3;
    private long styleId4;
    private int rate4;
    private long styleId5;
    private int rate5;


    public static class rate{
        public rate(long styleId, int rate) {
            this.styleId = styleId;
            this.rate = rate;
        }

        public long styleId;
        public int rate;
    }

    public List<rate> rateList(){
        List<rate> rateList = new ArrayList<>();
        rate r0 = new rate(styleId0, rate0);
        rate r1 = new rate(styleId1, rate1);
        rate r2 = new rate(styleId2, rate2);
        rate r3 = new rate(styleId3, rate3);
        rate r4 = new rate(styleId4, rate4);
        rate r5 = new rate(styleId5, rate5);

        rateList.add(r0);
        rateList.add(r1);
        rateList.add(r2);
        rateList.add(r3);
        rateList.add(r4);
        rateList.add(r5);

        return rateList;
    }
}
