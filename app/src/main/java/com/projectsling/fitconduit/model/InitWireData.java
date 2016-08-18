package com.projectsling.fitconduit.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanley on 8/17/2016.
 */
public class InitWireData {
    private List<String> mWirenames;
    private List<Double> mWireOds;

    /*private List<String> mConduitNames;
    private List<Double> mConduitArea;*/

    private List<String> mConduits;

    public InitWireData() {
        mWirenames = new ArrayList<>();
        mWireOds = new ArrayList<>();

        /*mConduitNames = new ArrayList<>();
        mConduitArea = new ArrayList<>();*/
        mConduits = new ArrayList<>();

        mWirenames.add("FO 12str SMF armored");
        mWirenames.add("FO 36str SMF armored");
        mWirenames.add("FO 48str SMF armored");
        mWirenames.add("FO 2str S/MMF breakout");
        mWirenames.add("FO 6str S/MMF breakout");
        mWirenames.add("FO 12str S/MMF breakout");
        mWirenames.add("FO 24str S/MMF breakout");
        mWirenames.add("FO 48str S/MMF breakout");
        mWirenames.add("FO 2str S/MMF rodent resist brkout");
        mWirenames.add("FO 6str S/MMF rodent resist brkout");
        mWirenames.add("FO 12str S/MMF rodent resist brkout");
        mWirenames.add("FO 24str S/MMF rodent resist brkout");
        mWirenames.add("FO 48str S/MMF rodent resist brkout");
        mWirenames.add("FO patchcord 3mm");
        mWirenames.add("Data CAT5e UTP Plenum (ADC)");
        mWirenames.add("Data CAT5e UTP 24AWG (General)");
        mWirenames.add("Data CAT5e ScTP 24AWG");
        mWirenames.add("Data CAT6 UTP 28AWG (Comtran)");
        mWirenames.add("Data CAT6 UTP Plenum (ADC)");
        mWirenames.add("Data CAT6 UTP 23AWG (General)");
        mWirenames.add("Data CAT6 ScTP 24AWG");
        mWirenames.add("Data T1 2PR 22AWG");
        mWirenames.add("Copper 2PR 22AWG");
        mWirenames.add("Copper 2PR 19AWG");
        mWirenames.add("Copper 3PR 22AWG (mic)");
        mWirenames.add("Copper 3PR 22AWG (asm)");
        mWirenames.add("Copper 4PR 22AWG");
        mWirenames.add("Copper 4PR 19AWG");
        mWirenames.add("Copper 6PR 22AWG");
        mWirenames.add("Copper 6PR 19AWG");
        mWirenames.add("Copper 6PR 16AWG");
        mWirenames.add("Copper 12PR 22AWG");
        mWirenames.add("Copper 25PR 24AWG");
        mWirenames.add("Copper 25PR 22AWG");
        mWirenames.add("Copper 50PR 24AWG");
        mWirenames.add("Copper 50PR 22AWG");
        mWirenames.add("Copper 75PR 22AWG");
        mWirenames.add("Copper 100PR 24AWG");
        mWirenames.add("Copper 100PR 22AWG");
        mWirenames.add("Copper 150PR 22AWG");
        mWirenames.add("Copper 200PR 24AWG");
        mWirenames.add("Copper 200PR 22AWG");
        mWirenames.add("Copper QUAD 22AWG");
        mWirenames.add("Speaker cable 1PR 18AWG");
        mWirenames.add("Coax RG6 quad shield plen.");
        mWirenames.add("Coax RG6 tri shield");
        mWirenames.add("Coax RG6 quad shield");
        mWirenames.add("Coax RG59 quad shield plen.");
        mWirenames.add("Coax RG59 tri shield");
        mWirenames.add("Coax RG59 quad shield");
        mWirenames.add("Coax RG11 quad shield plen.");
        mWirenames.add("Power Superv. 6PR 16AWG");
        mWirenames.add("Power Superv. 12PR 16AWG");
        mWirenames.add("Power Superv. 25PR 16AWG");
        mWirenames.add("Power Superv. 50PR 16AWG");
        mWirenames.add("Power THHN/THWN 16AWG");
        mWirenames.add("Power THHN/THWN 14AWG");
        mWirenames.add("Power THHN/THWN 12AWG");
        mWirenames.add("Power THHN/THWN 10AWG");
        mWirenames.add("Power THHN/THWN 8AWG");
        mWirenames.add("Power THHN/THWN 6AWG");
        mWirenames.add("Power THHN/THWN 4AWG");
        mWirenames.add("Power THHN/THWN 2AWG");
        mWirenames.add("Power THHN/THWN 1AWG");
        mWirenames.add("Power XHHW 14AWG");
        mWirenames.add("Power XHHW 12AWG");
        mWirenames.add("Power XHHW 10AWG");
        mWirenames.add("Power XHHW 8AWG");
        mWirenames.add("Power XHHW 6AWG");
        mWirenames.add("Power XHHW 4AWG");
        mWirenames.add("Power XHHW 2AWG");
        mWirenames.add("Power XHHW 1AWG");
        mWirenames.add("Power RHH/RHW 14AWG");
        mWirenames.add("Power RHH/RHW 12AWG");
        mWirenames.add("Power RHH/RHW 10AWG");
        mWirenames.add("Power RHH/RHW 8AWG");
        mWirenames.add("Power RHH/RHW 6AWG");
        mWirenames.add("Power RHH/RHW 4AWG");
        mWirenames.add("Power RHH/RHW 2AWG");
        mWirenames.add("Power RHH/RHW 1AWG");

        mWireOds.add(0.72);
        mWireOds.add(0.72);
        mWireOds.add(0.78);
        mWireOds.add(0.212);
        mWireOds.add(0.335);
        mWireOds.add(0.425);
        mWireOds.add(0.575);
        mWireOds.add(0.75);
        mWireOds.add(0.412);
        mWireOds.add(0.49);
        mWireOds.add(0.626);
        mWireOds.add(0.65);
        mWireOds.add(0.97);
        mWireOds.add(0.118);
        mWireOds.add(0.185);
        mWireOds.add(0.248);
        mWireOds.add(0.31);
        mWireOds.add(0.145);
        mWireOds.add(0.212);
        mWireOds.add(0.272);
        mWireOds.add(0.31);
        mWireOds.add(0.3);
        mWireOds.add(0.38);
        mWireOds.add(0.41);
        mWireOds.add(0.295);
        mWireOds.add(0.27);
        mWireOds.add(0.44);
        mWireOds.add(0.5);
        mWireOds.add(0.5);
        mWireOds.add(0.6);
        mWireOds.add(0.7);
        mWireOds.add(0.6);
        mWireOds.add(0.6);
        mWireOds.add(0.76);
        mWireOds.add(0.75);
        mWireOds.add(0.95);
        mWireOds.add(1.13);
        mWireOds.add(1.00);
        mWireOds.add(1.25);
        mWireOds.add(1.5);
        mWireOds.add(1.25);
        mWireOds.add(1.67);
        mWireOds.add(0.15);
        mWireOds.add(0.256);
        mWireOds.add(0.273);
        mWireOds.add(0.278);
        mWireOds.add(0.297);
        mWireOds.add(0.235);
        mWireOds.add(0.244);
        mWireOds.add(0.265);
        mWireOds.add(0.38);
        mWireOds.add(0.8);
        mWireOds.add(1.17);
        mWireOds.add(1.6);
        mWireOds.add(2.12);
        mWireOds.add(0.096);
        mWireOds.add(0.111);
        mWireOds.add(0.130);
        mWireOds.add(0.164);
        mWireOds.add(0.216);
        mWireOds.add(0.254);
        mWireOds.add(0.324);
        mWireOds.add(0.384);
        mWireOds.add(0.446);
        mWireOds.add(0.133);
        mWireOds.add(0.152);
        mWireOds.add(0.176);
        mWireOds.add(0.236);
        mWireOds.add(0.274);
        mWireOds.add(0.322);
        mWireOds.add(0.382);
        mWireOds.add(0.442);
        mWireOds.add(0.193);
        mWireOds.add(0.212);
        mWireOds.add(0.236);
        mWireOds.add(0.326);
        mWireOds.add(0.364);
        mWireOds.add(0.412);
        mWireOds.add(0.472);
        mWireOds.add(0.582);

        /*mConduitNames.add("RMC 1/2 in");
        mConduitNames.add("RMC 3/4 in");
        mConduitNames.add("RMC 1 in");
        mConduitNames.add("RMC 1 1/4 in");
        mConduitNames.add("RMC 1 1/2 in");
        mConduitNames.add("RMC 2 in");
        mConduitNames.add("RMC 2 1/2 in");
        mConduitNames.add("RMC 3 in");
        mConduitNames.add("RMC 4 in");
        mConduitNames.add("EMT 1/2 in");
        mConduitNames.add("EMT 3/4 in");
        mConduitNames.add("EMT 1 in");
        mConduitNames.add("EMT 1 1/4 in");
        mConduitNames.add("EMT 1 1/2 in");
        mConduitNames.add("EMT 2 in");
        mConduitNames.add("EMT 2 1/2 in");
        mConduitNames.add("EMT 3 in");
        mConduitNames.add("EMT 4 in");

        mConduitArea.add(0.314);
        mConduitArea.add(0.549);
        mConduitArea.add(0.887);
        mConduitArea.add(1.526);
        mConduitArea.add(2.071);
        mConduitArea.add(3.408);
        mConduitArea.add(4.866);
        mConduitArea.add(7.499);
        mConduitArea.add(12.882);
        mConduitArea.add(0.304);
        mConduitArea.add(0.533);
        mConduitArea.add(0.864);
        mConduitArea.add(1.496);
        mConduitArea.add(2.036);
        mConduitArea.add(3.356);
        mConduitArea.add(5.858);
        mConduitArea.add(8.846);
        mConduitArea.add(14.753);*/

        //RMC conduits
        mConduits.add("{" +
                "\"type\":" + "\"RMC\"" + "," +
                "\"diameter\":" + "0.5" + "," +
                "\"area\":" + "0.314" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"RMC\"" + "," +
                "\"diameter\":" + "0.75" + "," +
                "\"area\":" + "0.549" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"RMC\"" + "," +
                "\"diameter\":" + "1" + "," +
                "\"area\":" + "0.887" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"RMC\"" + "," +
                "\"diameter\":" + "1.25" + "," +
                "\"area\":" + "1.526" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"RMC\"" + "," +
                "\"diameter\":" + "1.5" + "," +
                "\"area\":" + "2.071" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"RMC\"" + "," +
                "\"diameter\":" + "2" + "," +
                "\"area\":" + "3.408" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"RMC\"" + "," +
                "\"diameter\":" + "2.5" + "," +
                "\"area\":" + "4.866" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"RMC\"" + "," +
                "\"diameter\":" + "3" + "," +
                "\"area\":" + "7.499" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"RMC\"" + "," +
                "\"diameter\":" + "4" + "," +
                "\"area\":" + "12.882" + "}");

        //EMT Conduits
        mConduits.add("{" +
                "\"type\":" + "\"EMT\"" + "," +
                "\"diameter\":" + "0.5" + "," +
                "\"area\":" + "0.304" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"EMT\"" + "," +
                "\"diameter\":" + "0.75" + "," +
                "\"area\":" + "0.533" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"EMT\"" + "," +
                "\"diameter\":" + "1" + "," +
                "\"area\":" + "0.864" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"EMT\"" + "," +
                "\"diameter\":" + "1.25" + "," +
                "\"area\":" + "1.496" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"EMT\"" + "," +
                "\"diameter\":" + "1.5" + "," +
                "\"area\":" + "2.036" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"EMT\"" + "," +
                "\"diameter\":" + "2" + "," +
                "\"area\":" + "3.356" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"EMT\"" + "," +
                "\"diameter\":" + "2.5" + "," +
                "\"area\":" + "5.858" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"EMT\"" + "," +
                "\"diameter\":" + "3" + "," +
                "\"area\":" + "8.846" + "}");
        mConduits.add("{" +
                "\"type\":" + "\"EMT\"" + "," +
                "\"diameter\":" + "4" + "," +
                "\"area\":" + "14.753" + "}");
    }

    public String getWireDataPairString(int index) {
        return "{" +
                "\"name\":" + "\"" + mWirenames.get(index) + "\"," +
                "\"od\":" + mWireOds.get(index) + "}";
    }

    public String getConduitDataPairString(int index) {
        return mConduits.get(index);
    }

    public int getWireListAmount() {
        return mWirenames.size();
    }

    public int getConduitListAmount() {
        return mConduits.size();
    }
}
