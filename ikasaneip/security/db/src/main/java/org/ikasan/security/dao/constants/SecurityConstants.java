/*
 * $Id$
 * $URL$
 * 
 * ====================================================================
 * Ikasan Enterprise Integration Platform
 * 
 * Distributed under the Modified BSD License.
 * Copyright notice: The copyright for this software and a full listing 
 * of individual contributors are as shown in the packaged copyright.txt 
 * file. 
 * 
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 *  - Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer.
 *
 *  - Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 *
 *  - Neither the name of the ORGANIZATION nor the names of its contributors may
 *    be used to endorse or promote products derived from this software without 
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE 
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE 
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 */
package org.ikasan.security.dao.constants;

/**
 * @author CMI2 Development Team
 *
 */
public interface SecurityConstants
{
	public static final String AUTH_METHOD_LOCAL = "AUTH_METHOD_LOCAL";
	public static final String AUTH_METHOD_DASHBOARD = "AUTH_METHOD_DASHBOARD";
	public static final String AUTH_METHOD_LDAP_LOCAL = "AUTH_METHOD_LDAP_LOCAL";
	public static final String AUTH_METHOD_LDAP = "AUTH_METHOD_LDAP";
    
	public static final Long AUTH_METHOD_ID = Long.valueOf(1);
	
	public static final String PRINCIPAL_ID = "principalId";
	
	public static final String GET_USERS_BY_PRINCIPAL_QUERY = "select u from UserPrincipal as up," +
            " User as u " +
            " where  u.id = up.id.userId" +
            " and up.id.ikasanPrincipalId = :" + PRINCIPAL_ID;

    public static final String GET_POLICY_WITH_ROLE_QUERY = """
        select p from Policy as p, RolePolicy as rp, Role as r \
        where  r.name = :name and rp.id.roleId = r.id and rp.id.policyId = p.id \
        """ ;

    public static final String GET_IKASAN_PRINCIPLE_WITH_ROLE_QUERY = """
        select p from IkasanPrincipal as p \
         LEFT JOIN FETCH p.roles r \
         where  r.name = :name\
        """ ;

    public static final String GET_IKASAN_PRINCIPLE_LITE_WITH_ROLE_QUERY = """
        select p from IkasanPrincipalLite as p, Role r, PrincipalRole pr \
        where
            p.id = pr.id.ikasanPrincipalId
        and
            r.id = pr.id.roleId
        and           
            r.name = :name
        """;

    public static final String GET_IKASAN_PRINCIPAL_LITE_IDS_WITH_ROLE_QUERY = """
        select p.id from IkasanPrincipalLite as p, Role r, PrincipalRole pr \
        where
            p.id = pr.id.ikasanPrincipalId
        and
            r.id = pr.id.roleId
        and           
            r.name = :name
        """;

    public static final String GET_IKASAN_PRINCIPLE_LITE_WITH_ROLE_QUERY_COUNT = """
        select count(distinct p) from IkasanPrincipalLite as p, Role r, PrincipalRole pr \
        where
            p.id = pr.id.ikasanPrincipalId
        and
            r.id = pr.id.roleId
        and           
            r.name = :name
        """;

    public static final String GET_USERS_WITH_ROLE_QUERY = """
        select u from UserLite u, IkasanPrincipal as p, UserPrincipal up, Role r, PrincipalRole pr \
        where  
            u.id = up.id.userId
        and
            p.id = up.id.ikasanPrincipalId
        and
            p.id = pr.id.ikasanPrincipalId
        and
            r.id = pr.id.roleId
        and
            p.type = 'user'
        and           
            r.name = :name
        """;

    public static final String GET_USER_IDS_WITH_ROLE_QUERY = """
        select u.id from UserLite u, IkasanPrincipal as p, UserPrincipal up, Role r, PrincipalRole pr \
        where  
            u.id = up.id.userId
        and
            p.id = up.id.ikasanPrincipalId
        and
            p.id = pr.id.ikasanPrincipalId
        and
            r.id = pr.id.roleId
        and
            p.type = 'user'
        and           
            r.name = :name
        """;

    public static final String GET_USERS_WITH_ROLE_COUNT_QUERY = """
        select count(distinct u) from UserLite u, IkasanPrincipal as p, UserPrincipal up, Role r, PrincipalRole pr \
        where  
            u.id = up.id.userId
        and
            p.id = up.id.ikasanPrincipalId
        and
            p.id = pr.id.ikasanPrincipalId
        and
            r.id = pr.id.roleId
        and
            p.type = 'user'
        and           
            r.name = :name
        """;

    public static final String GET_IKASAN_PRINCIPLE_WITH_ROLE_IN_QUERY = """
        select distinct(p) from IkasanPrincipal as p \
         LEFT JOIN FETCH p.roles r \
         where  r.name in (:name)\
        """ ;

    public static final String GET_ROLE_JOB_PLANS_BY_ROLE_QUERY = """
        select rjp from RoleJobPlan as rjp \
         where  rjp.jobPlanName = :name\
        """ ;
}
