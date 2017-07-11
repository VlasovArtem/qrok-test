package org.avlasov.qrok.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by artemvlasov on 11/07/2017.
 */
@TestConfiguration
@ComponentScan(basePackages = {"org.avlasov.qrok.controller", "org.avlasov.qrok.service"})
public class ControllerConfig {
}
