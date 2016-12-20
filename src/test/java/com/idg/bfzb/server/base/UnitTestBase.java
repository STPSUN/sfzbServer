package com.idg.bfzb.server.base;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class UnitTestBase {
    private final static String BASE_TOKEN =
            "39B7FB03E2A1E0B057D1D7CC6714EE2CC9A56E9EDFF415157DE39BE1AC64815FB929DC15E746350FF820FF2CEE62DF950169958BCBF4E1258311639D38EA15F0";
    private final static Long EXPIRE_TIME = 360000L;
    protected MockMvc mockmvc;
}
