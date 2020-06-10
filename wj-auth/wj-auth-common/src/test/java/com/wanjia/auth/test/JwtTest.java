package com.wanjia.auth.test;

import com.wanjia.common.pojo.UserInfo;
import com.wanjia.common.utils.JwtUtils;
import com.wanjia.common.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "D:\\project\\tmp\\rsa\\rsa.pub";

    private static final String priKeyPath = "D:\\project\\tmp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU5MDc0MTU1Nn0.IH4Vn8j9T89HWRdgqz07GNFbbFD12hlpiY5D7MQzc0Pl6Par0lqWzLOVdWyhCSiMN43FU18RMDwN_QG3DB6aULJZjhBqcCd9tIK0nyypfscpNKPNUmZEZagHU5bHC2I5K2BFHioS1gNeO9fpocj4Ln0gMf29O2UxRz9_J0Y5ZV0";

        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}