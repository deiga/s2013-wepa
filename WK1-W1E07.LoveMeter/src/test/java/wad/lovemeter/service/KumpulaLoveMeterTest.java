package wad.lovemeter.service;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import static org.junit.Assert.*;
import org.junit.Test;

@Points("W1E07.1")
public class KumpulaLoveMeterTest {
    private static final String LOVE_METER_CLASSNAME = "wad.lovemeter.service.KumpulaLoveMeter";
    private Class loveMeter;

    @Test
    public void classKumpulaLoveMeterExists() {
        loveMeter = ReflectionUtils.findClass(LOVE_METER_CLASSNAME);
    }

    @Test
    public void implementsInterfaceLoveMeterService() {
        classKumpulaLoveMeterExists();
        
        boolean found = false;
        
        for (Class iface : loveMeter.getInterfaces()) {
            if(LoveMeterService.class.equals(iface)) {
                found = true;
                break;
            }
        }
        
        if(!found) {
            fail("Make sure that class KumpulaLoveMeter implements interface LoveMeterService.");
        }
    }
    
    

    @Test
    public void algorithmCorrect() throws Throwable {
        implementsInterfaceLoveMeterService();
        
        LoveMeterService lovah = (LoveMeterService) Reflex.reflect(LOVE_METER_CLASSNAME).constructor().takingNoParams().invoke();

        assertEquals("Match level for \"mikke\" and \"kasper\" should be 80. Verify your algorithm.", lovah.match("mikke", "kasper"), 80);
        assertEquals("Match level for \"kasper\" and \"arto\" should be 51. Verify your algorithm.", lovah.match("kasper", "arto"), 51);
        assertEquals("Match level for \"arto\" and \"mikke\" should be 74. Verify your algorithm.", lovah.match("arto", "mikke"), 74);
    }
}
