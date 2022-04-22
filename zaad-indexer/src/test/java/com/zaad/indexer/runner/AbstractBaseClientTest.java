package com.zaad.indexer.runner;

import com.carrotsearch.randomizedtesting.RandomizedTest;
import org.elasticsearch.test.ESIntegTestCase;
import org.junit.Before;

/**
 * ES JAVA 클라이언트 테스트를 위한 베이스 클래스. 각 유닛 테스트의 독립성을 위해 클러스터를 공유하지 않고 실행
 *
 * @author lks21c
 */
@ESIntegTestCase.ClusterScope(scope = ESIntegTestCase.Scope.TEST)
public class AbstractBaseClientTest extends ESIntegTestCase {

    @Override
    @Before
    public void setUp() throws Exception {
        System.setProperty("tests.security.manager", "false");
        boolean securityManager = RandomizedTest.systemPropertyAsBoolean("tests.security.manager", false);
        System.out.println("securityManager = " + securityManager);
        super.setUp();
    }
}
