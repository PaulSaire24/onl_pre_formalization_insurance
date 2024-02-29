package com.bbva.rbvd.rbvd118;

import com.bbva.elara.domain.transaction.Context;
import com.bbva.elara.domain.transaction.ThreadContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.aop.framework.Advised;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/META-INF/spring/RBVDR118-app.xml",
        "classpath:/META-INF/spring/RBVDR118-app-test.xml",
        "classpath:/META-INF/spring/RBVDR118-arc.xml",
        "classpath:/META-INF/spring/RBVDR118-arc-test.xml"})
public class RBVDR118Test {

    @Spy
    private Context context;

    @Resource(name = "rbvdR118")
    private RBVDR118 rbvdR118;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        context = new Context();
        ThreadContext.set(context);
        getObjectIntrospection();
    }

    private void getObjectIntrospection() throws Exception {
        Object result = this.rbvdR118;
        if (this.rbvdR118 instanceof Advised) {
            Advised advised = (Advised) this.rbvdR118;
            result = advised.getTargetSource().getTarget();
        }
    }

    @Test
    public void executeTest() {
        //TODO: Implementar test
    }

}
