package org.hyperic.bootstrap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.hyperic.sigar.SigarException;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test of the {@link TomcatEngineController}
 * @author jhickey
 * 
 */
public class TomcatEngineControllerTest {

    private ProcessManager processManager;
    private String serverHome = "/Applications/Evolution/server-5.0.0-EE";
    private String engineHome = "/Applications/Evolution/server-5.0.0-EE/hq-engine";
    private String catalinaHome = engineHome + "/hq-server";
    private String catalinaBase = catalinaHome;
    private TomcatEngineController tomcatEngineController;

    @Before
    public void setUp() {
        this.processManager = EasyMock.createMock(ProcessManager.class);
        this.tomcatEngineController = new TomcatEngineController(processManager, engineHome,
            serverHome);
    }

    @Test
    public void testStart() throws MalformedURLException {
        final List<String> expectedOpts = new ArrayList<String>();
        expectedOpts.add("-XX:MaxPermSize=192m");
        expectedOpts.add("-Xmx512m");
        expectedOpts.add("-Xms512m");
        expectedOpts.add("-XX:+HeapDumpOnOutOfMemoryError");
        expectedOpts.add("-Dserver.home=" + serverHome);

        EasyMock
            .expect(
                processManager
                    .executeProcess(
                        EasyMock
                            .aryEq(new String[] { "java",
                                                 "-cp",
                                                 catalinaHome + "/bin/bootstrap.jar",
                                                 "-XX:MaxPermSize=192m",
                                                 "-Xmx512m",
                                                 "-Xms512m",
                                                 "-XX:+HeapDumpOnOutOfMemoryError",
                                                 "-Dserver.home=" + serverHome,
                                                 "-Dcatalina.config=" + new File(catalinaBase +
                                                 "/conf/hq-catalina.properties").toURI().toURL().toString(),
                                                 "-Dcom.sun.management.jmxremote",
                                                 "-Djava.endorsed.dirs" + catalinaHome +
                                                     "/endorsed",
                                                 "-Dcatalina.base=" + catalinaBase,
                                                 "-Dcatalina.home=" + catalinaHome,
                                                 "-Djava.io.tmpdir=" + catalinaBase + "/temp",
                                                 "-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager",
                                                 "org.apache.catalina.startup.Bootstrap",
                                                 "start" }), EasyMock.eq(serverHome), EasyMock
                            .eq(true), EasyMock.eq(-1))).andReturn(0);
        replay();
        int exitCode = tomcatEngineController.start(expectedOpts);
        verify();
        assertEquals(0, exitCode);
    }

    @Test
    public void testStop() throws Exception {
        EasyMock.expect(
            processManager.getPidFromProcQuery("State.Name.sw=java,Args.*.eq=-Dcatalina.base=" +
                                               catalinaBase)).andReturn(123l);
        processManager.kill(123l);
        EasyMock.expect(processManager.waitForProcessDeath(60, 123l)).andReturn(true);
        replay();
        boolean stopped = tomcatEngineController.stop();
        verify();
        assertTrue(stopped);
    }

    @Test
    public void testStopHaveToHalt() throws Exception {
        EasyMock.expect(
            processManager.getPidFromProcQuery("State.Name.sw=java,Args.*.eq=-Dcatalina.base=" +
                                               catalinaBase)).andReturn(123l);
        processManager.kill(123l);
        EasyMock.expect(processManager.waitForProcessDeath(60, 123l)).andReturn(false);
        processManager.forceKill(123l);
        EasyMock.expect(processManager.waitForProcessDeath(1, 123l)).andReturn(true);
        replay();
        boolean stopped = tomcatEngineController.stop();
        verify();
        assertTrue(stopped);
    }

    @Test
    public void testStopEventHaltDoesntWork() throws Exception {
        EasyMock.expect(
            processManager.getPidFromProcQuery("State.Name.sw=java,Args.*.eq=-Dcatalina.base=" +
                                               catalinaBase)).andReturn(123l);
        processManager.kill(123l);
        EasyMock.expect(processManager.waitForProcessDeath(60, 123l)).andReturn(false);
        processManager.forceKill(123l);
        EasyMock.expect(processManager.waitForProcessDeath(1, 123l)).andReturn(false);
        replay();
        boolean stopped = tomcatEngineController.stop();
        verify();
        assertFalse(stopped);
    }

    @Test
    public void testStopNotRunning() throws Exception {
        EasyMock.expect(
            processManager.getPidFromProcQuery("State.Name.sw=java,Args.*.eq=-Dcatalina.base=" +
                                               catalinaBase)).andReturn(-1l);
        replay();
        boolean stopped = tomcatEngineController.stop();
        verify();
        assertTrue(stopped);
    }

    @Test
    public void testHalt() throws SigarException {
        EasyMock.expect(
            processManager.getPidFromProcQuery("State.Name.sw=java,Args.*.eq=-Dcatalina.base=" +
                                               catalinaBase)).andReturn(123l);
        processManager.forceKill(123l);
        replay();
        tomcatEngineController.halt();
        verify();
    }

    @Test
    public void testIsEngineRunning() throws SigarException {
        EasyMock.expect(
            processManager.getPidFromProcQuery("State.Name.sw=java,Args.*.eq=-Dcatalina.base=" +
                                               catalinaBase)).andReturn(123l);
        replay();
        boolean running = tomcatEngineController.isEngineRunning();
        verify();
        assertTrue(running);
    }

    @Test
    public void testIsEngineRunningNotRunning() throws SigarException {
        EasyMock.expect(
            processManager.getPidFromProcQuery("State.Name.sw=java,Args.*.eq=-Dcatalina.base=" +
                                               catalinaBase)).andReturn(-1l);
        replay();
        boolean running = tomcatEngineController.isEngineRunning();
        verify();
        assertFalse(running);
    }

    private void replay() {
        EasyMock.replay(processManager);
    }

    private void verify() {
        EasyMock.verify(processManager);
    }
}