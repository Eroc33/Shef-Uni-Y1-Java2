package uk.ac.sheffield.aca15er;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by Euan Rochester on 15/04/2016.
 */
public class ParamSelectDialog extends JDialog implements ActionListener {
    private final JComboBox<PlaceInfo> placeSelect;
    private ContainableObservable observable = new ContainableObservable();
    private JSpinner dateSelect;

    private static final java.util.List<String> icaoLocations;
    private static final Map<String,String> icaoPrefixToIsoCode;
    static{
        Map<String, String> prefixes = new HashMap<>();
        prefixes.put("AG","sb");
        prefixes.put("AN","nr");
        prefixes.put("AY","pg");

        prefixes.put("BG","gl");
        prefixes.put("BI","is");
        prefixes.put("BK","xk");

        prefixes.put("CY","ca");
        prefixes.put("CZ","ca");

        prefixes.put("DA","dz");
        prefixes.put("DB","bj");
        prefixes.put("DF","bf");
        prefixes.put("DG","gh");
        prefixes.put("DI","ci");
        prefixes.put("DN","ng");
        prefixes.put("DR","ne");
        prefixes.put("DT","tn");
        prefixes.put("DX","tg");

        prefixes.put("EB","be");
        prefixes.put("ED","de");
        prefixes.put("EE","ee");
        prefixes.put("EF","fi");
        prefixes.put("EG","gb");
        prefixes.put("EH","nl");
        prefixes.put("EI","ie");
        prefixes.put("EK","dk");
        prefixes.put("EL","lu");
        prefixes.put("EN","no");
        prefixes.put("EP","pl");
        prefixes.put("ES","se");
        prefixes.put("ET","de");
        prefixes.put("EV","lv");
        prefixes.put("EY","lt");

        prefixes.put("FA","za");
        prefixes.put("FB","bw");
        prefixes.put("FC","cg");
        prefixes.put("FD","sz");
        prefixes.put("FE","cf");
        prefixes.put("FG","gq");
        prefixes.put("FH","sh");
        prefixes.put("FI","mu");
        prefixes.put("FJ","io");
        prefixes.put("FK","cm");
        prefixes.put("FL","zm");
        prefixes.put("FM","mg");
        prefixes.put("FN","ao");
        prefixes.put("FO","ga");
        prefixes.put("FP","st");
        prefixes.put("FQ","mz");
        prefixes.put("FS","sc");
        prefixes.put("FT","td");
        prefixes.put("FV","zw");
        prefixes.put("FW","mw");
        prefixes.put("FX","ls");
        prefixes.put("FY","na");
        prefixes.put("FZ","cd");

        prefixes.put("GA","ml");
        prefixes.put("GB","gm");
        prefixes.put("GC","es");
        prefixes.put("GE","es");
        prefixes.put("GF","sl");
        prefixes.put("GG","gw");
        prefixes.put("GL","lr");
        prefixes.put("GM","ma");
        prefixes.put("GO","sn");
        prefixes.put("GQ","mr");
        prefixes.put("GS","eh");
        prefixes.put("GU","gn");
        prefixes.put("GV","cv");

        prefixes.put("HA","et");
        prefixes.put("HB","bi");
        prefixes.put("HC","so");
        prefixes.put("HD","dj");
        prefixes.put("HE","eg");
        prefixes.put("HH","er");
        prefixes.put("HK","ke");
        prefixes.put("HL","ly");
        prefixes.put("HR","rw");
        prefixes.put("HS","sd");
        prefixes.put("HT","tz");
        prefixes.put("HU","ug");

        prefixes.put("KA","us");
        prefixes.put("KB","us");
        prefixes.put("KC","us");
        prefixes.put("KD","us");
        prefixes.put("KE","us");
        prefixes.put("KF","us");
        prefixes.put("KG","us");
        prefixes.put("KH","us");
        prefixes.put("KI","us");
        prefixes.put("KJ","us");
        prefixes.put("KK","us");
        prefixes.put("KL","us");
        prefixes.put("KM","us");
        prefixes.put("KN","us");
        prefixes.put("KO","us");
        prefixes.put("KP","us");
        prefixes.put("KQ","us");
        prefixes.put("KR","us");
        prefixes.put("KS","us");
        prefixes.put("KT","us");
        prefixes.put("KU","us");
        prefixes.put("KV","us");
        prefixes.put("KW","us");
        prefixes.put("KX","us");
        prefixes.put("KY","us");
        prefixes.put("KZ","us");

        prefixes.put("LA","al");
        prefixes.put("LB","bg");
        prefixes.put("LC","cy");
        prefixes.put("LD","hr");
        prefixes.put("LE","es");
        prefixes.put("LF","fr");
        prefixes.put("LG","gr");
        prefixes.put("LH","hu");
        prefixes.put("LI","it");
        prefixes.put("LJ","si");
        prefixes.put("LK","cz");
        prefixes.put("LL","il");
        prefixes.put("LM","mt");
        prefixes.put("LN","mc");
        prefixes.put("LO","at");
        prefixes.put("LP","pt");
        prefixes.put("LQ","ba");
        prefixes.put("LR","ro");
        prefixes.put("LS","ch");
        prefixes.put("LT","tr");
        prefixes.put("LU","md");
        prefixes.put("LV","ps");
        prefixes.put("LW","mk");
        prefixes.put("LX","gi");
        prefixes.put("LY","rs");
        prefixes.put("LZ","sk");

        prefixes.put("MB","tc");
        prefixes.put("MD","do");
        prefixes.put("MG","gt");
        prefixes.put("MH","hn");
        prefixes.put("MK","jm");
        prefixes.put("MM","mx");
        prefixes.put("MN","ni");
        prefixes.put("MP","pa");
        prefixes.put("MR","cr");
        prefixes.put("MS","sv");
        prefixes.put("MT","ht");
        prefixes.put("MU","cu");
        prefixes.put("MW","ky");
        prefixes.put("MY","bs");
        prefixes.put("MZ","bz");

        prefixes.put("NC","ck");
        prefixes.put("NF","fj");
        prefixes.put("NG","ki");
        prefixes.put("NI","nu");
        prefixes.put("NL","fr");
        prefixes.put("NS","ws");
        prefixes.put("NT","fr");
        prefixes.put("NV","vu");
        prefixes.put("NW","fr");
        prefixes.put("NZ","nz");

        prefixes.put("OA","af");
        prefixes.put("OB","bh");
        prefixes.put("OE","sa");
        prefixes.put("OI","ir");
        prefixes.put("OJ","jo");
        prefixes.put("OK","kw");
        prefixes.put("OL","lb");
        prefixes.put("OM","ae");
        prefixes.put("OO","om");
        prefixes.put("OP","pk");
        prefixes.put("OR","iq");
        prefixes.put("OS","sy");
        prefixes.put("OT","qa");
        prefixes.put("OY","ye");

        prefixes.put("PA","ak");
        prefixes.put("PB","us");
        prefixes.put("PC","ki");
        prefixes.put("PF","ak");
        prefixes.put("PG","gu");
        prefixes.put("PH","hi");
        prefixes.put("PJ","us");
        prefixes.put("PK","mh");
        prefixes.put("PL","ki");
        prefixes.put("PM","us");
        prefixes.put("PO","ak");
        prefixes.put("PP","ak");
        prefixes.put("PT","fm");
        prefixes.put("PW","us");

        prefixes.put("RC","tw");
        prefixes.put("RJ","jp");
        prefixes.put("RK","kr");
        prefixes.put("RO","jp");
        prefixes.put("RP","ph");

        prefixes.put("SA","ar");
        prefixes.put("SB","br");
        prefixes.put("SC","cl");
        prefixes.put("SD","br");
        prefixes.put("SE","ec");
        prefixes.put("SF","fk");
        prefixes.put("SG","py");
        prefixes.put("SH","cl");
        prefixes.put("SI","br");
        prefixes.put("SJ","br");
        prefixes.put("SK","co");
        prefixes.put("SL","bo");
        prefixes.put("SM","sr");
        prefixes.put("SN","br");
        prefixes.put("SO","fr");
        prefixes.put("SP","pe");
        prefixes.put("SS","br");
        prefixes.put("SU","uy");
        prefixes.put("SV","ve");
        prefixes.put("SW","br");
        prefixes.put("SY","gy");

        prefixes.put("TA","ag");
        prefixes.put("TB","bb");
        prefixes.put("TD","dm");
        prefixes.put("TF","fr");
        prefixes.put("TG","gd");
        prefixes.put("TI","vi");
        prefixes.put("TJ","pr");
        prefixes.put("TK","kn");
        prefixes.put("TL","lc");
        prefixes.put("TN","nl");
        prefixes.put("TQ","ai");
        prefixes.put("TR","ms");
        prefixes.put("TT","tt");
        prefixes.put("TU","vg");
        prefixes.put("TV","vc");
        prefixes.put("TX","bm");

        prefixes.put("UE","ru");
        prefixes.put("UH","ru");
        prefixes.put("UI","ru");
        prefixes.put("UL","ru");
        prefixes.put("UM","ru");
        prefixes.put("UN","ru");
        prefixes.put("UO","ru");
        prefixes.put("UR","ru");
        prefixes.put("US","ru");
        prefixes.put("UU","ru");
        prefixes.put("UW","ru");
        prefixes.put("UA","kz");
        prefixes.put("UB","az");
        prefixes.put("UC","kg");
        prefixes.put("UD","am");
        prefixes.put("UG","ge");
        prefixes.put("UK","ua");
        prefixes.put("UM","by");
        prefixes.put("UT","tj");

        prefixes.put("VA","in");
        prefixes.put("VC","lk");
        prefixes.put("VD","kh");
        prefixes.put("VE","in");
        prefixes.put("VG","bd");
        prefixes.put("VH","hk");
        prefixes.put("VI","in");
        prefixes.put("VL","la");
        prefixes.put("VM","mo");
        prefixes.put("VN","np");
        prefixes.put("VO","in");
        prefixes.put("VQ","bt");
        prefixes.put("VR","mv");
        prefixes.put("VT","th");
        prefixes.put("VV","vn");
        prefixes.put("VY","mm");

        prefixes.put("WA","id");
        prefixes.put("WB","bn");
        prefixes.put("WI","id");
        prefixes.put("WM","my");
        prefixes.put("WP","tl");
        prefixes.put("WQ","id");
        prefixes.put("WR","id");
        prefixes.put("WS","sg");

        prefixes.put("YA","au");
        prefixes.put("YB","au");
        prefixes.put("YC","au");
        prefixes.put("YD","au");
        prefixes.put("YE","au");
        prefixes.put("YF","au");
        prefixes.put("YG","au");
        prefixes.put("YH","au");
        prefixes.put("YI","au");
        prefixes.put("YJ","au");
        prefixes.put("YK","au");
        prefixes.put("YL","au");
        prefixes.put("YM","au");
        prefixes.put("YN","au");
        prefixes.put("YO","au");
        prefixes.put("YP","au");
        prefixes.put("YQ","au");
        prefixes.put("YR","au");
        prefixes.put("YS","au");
        prefixes.put("YT","au");
        prefixes.put("YU","au");
        prefixes.put("YV","au");
        prefixes.put("YW","au");
        prefixes.put("YX","au");
        prefixes.put("YY","au");

        prefixes.put("ZB","cn");
        prefixes.put("ZG","cn");
        prefixes.put("ZH","cn");
        prefixes.put("ZJ","cn");
        prefixes.put("ZL","cn");
        prefixes.put("ZP","cn");
        prefixes.put("ZS","cn");
        prefixes.put("ZU","cn");
        prefixes.put("ZW","cn");
        prefixes.put("ZY","cn");
        prefixes.put("ZK","kp");
        prefixes.put("ZM","mn");


        icaoPrefixToIsoCode = Collections.unmodifiableMap(prefixes);

        java.util.List<String> locs = new ArrayList<>();
        locs.add("");
        icaoLocations = Collections.unmodifiableList(locs);
    }

    public ParamSelectDialog(JFrame frame, boolean modal){
        super(frame,modal);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        placeSelect = new JComboBox<PlaceInfo>();
        try {
            BufferedReader file = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("icao_codes.txt")));
            String line;
            while((line = file.readLine())!= null){
                String[] parts = line.split(",");
                if(parts.length != 2){
                    throw new RuntimeException("Expected 2 entries per line in icao_codes.txt");
                }
                placeSelect.addItem(new PlaceInfo(parts[0],parts[1]));
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ListCellRenderer<PlaceInfo> renderer = new LocationRenderer();
        placeSelect.setRenderer(renderer);
        add(placeSelect);

        dateSelect = new JSpinner();
        dateSelect.setModel(new SpinnerDateModel());
        dateSelect.setEditor(new JSpinner.DateEditor(dateSelect,"dd/MM/yyyy"));
        add(dateSelect);
        JButton btn = new JButton("Select");
        add(btn);
        btn.addActionListener(this);
        pack();
    }

    public void watch(Observer obs){
        observable.addObserver(obs);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        observable.setChanged();
        observable.notifyObservers(this);
    }

    public Date getDate(){
        return (Date) dateSelect.getModel().getValue();
    }

    public String getPlace() {
        return ((PlaceInfo) placeSelect.getSelectedItem()).getCode();
    }

    private class LocationRenderer implements ListCellRenderer<PlaceInfo> {
        //cache the icons so we don't use ne memory for the same flag, and don't have to reload the files
        Map<String,ImageIcon> iconCache = new HashMap<String, ImageIcon>();
        @Override
        public Component getListCellRendererComponent(JList<? extends PlaceInfo> list, PlaceInfo value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = new JLabel(value.toString());
            String isoCode = icaoPrefixToIsoCode.get(value.codePrefix());
            try {
                if(isoCode != null){
                    ImageIcon icon = iconCache.get(isoCode);
                    if(icon == null){
                        URL url = getClass().getResource("flag-icons/"+isoCode+".svg.png");
                        //does the icon exist?
                        if(url != null) {
                            //yes? load & cache it
                            icon = new ImageIcon(url);
                        }else{
                            //no? use an empty icon so we can cache it
                            icon = new ImageIcon();
                        }
                        iconCache.put(isoCode, icon);
                    }
                    label.setIcon(icon);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            label.setBorder(new EmptyBorder(5,5,5,5));
            return label;
        }
    }

    private class PlaceInfo {
        private final String description;
        private final String code;

        public PlaceInfo(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String codePrefix() {
            return code.substring(0,2);
        }

        @Override
        public String toString() {
            return code+ "-" +description;
        }

        public String getCode() {
            return code;
        }
    }
}
