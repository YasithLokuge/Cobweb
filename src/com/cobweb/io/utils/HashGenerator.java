/*******************************************************************************
 * Copyright  (c) 2015-2016, Cobweb IO (http://www.cobweb.io) All Rights Reserved.
 *   
 * Cobweb IO licences this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.cobweb.io.utils;
 
import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.SimpleByteSource;
 
public class HashGenerator{

    public String generateSalt() {
    	return new BigInteger(250, new SecureRandom()).toString(32);
    }    
    
    public String generateHash(String password){
    	Sha256Hash hash = new Sha256Hash(password);
    	return hash.toHex();
    }
    
    public String saltHashPassword(String password, String salt) {        
        Sha256Hash hash = new Sha256Hash(password, (new SimpleByteSource(salt)).getBytes());        
        return hash.toHex();
    }
    
}
