package com.petstore;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(2)
public class PetStoreSecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

}