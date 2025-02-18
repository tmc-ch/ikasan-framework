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

package org.ikasan.security.service;

import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.ikasan.security.SecurityAutoConfiguration;
import org.ikasan.security.SecurityTestAutoConfiguration;
import org.ikasan.security.dao.SecurityDao;
import org.ikasan.security.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Unit test for {@link SecurityService}
 * 
 * @author CMI2 Development Team
 *
 */
@SuppressWarnings("unqualified-field-access")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SecurityAutoConfiguration.class, SecurityTestAutoConfiguration.class})
public class SecurityServiceTest
{
    /** Object being tested */
    @Autowired
    private SecurityDao xaSecurityDao;
    @Autowired
    private SecurityService xaSecurityService;


    /**
     * Before each test case, inject a mock {@link HibernateTemplate} to dao implementation
     * being tested
     */
    @Before public void setup()
    {
        HashSet<Role> roles = new HashSet<Role>();
        HashSet<Policy> policies = new HashSet<Policy>();

        for(int i=0; i<10; i++)
        {
            Role role = new Role();
            role.setName("role" + i);

            for(int j=0; j<10; j++)
            {
                Policy policy = new Policy();
                policy.setName("policy" + j + i);
                policy.setDescription("description");
                this.xaSecurityDao.saveOrUpdatePolicy(policy);
                policies.add(policy);
            }

            role.setPolicies(policies);
            role.setDescription("description");
            this.xaSecurityDao.saveOrUpdateRole(role);
            roles.add(role);
            policies = new HashSet<Policy>();
        }

    	IkasanPrincipal principal = new IkasanPrincipal();
    	principal.setName("stewmi");
    	principal.setType("type");
    	principal.setRoles(roles);
    	principal.setDescription("description");

    	this.xaSecurityDao.saveOrUpdatePrincipal(principal);

    	principal = new IkasanPrincipal();
        principal.setName("anotherPrincipal1");
        principal.setType("type");
        principal.setDescription("description");
        principal.setRoles(roles);

        this.xaSecurityDao.saveOrUpdatePrincipal(principal);
        
        principal = new IkasanPrincipal();
        principal.setName("anotherPrincipal2");
        principal.setType("type");
        principal.setDescription("description");
        principal.setRoles(roles);

        this.xaSecurityDao.saveOrUpdatePrincipal(principal);
        
        principal = new IkasanPrincipal();
        principal.setName("anotherPrincipal3");
        principal.setType("type");
        principal.setDescription("description");
        principal.setRoles(roles);

        this.xaSecurityDao.saveOrUpdatePrincipal(principal);
        
        principal = new IkasanPrincipal();
        principal.setName("anotherPrincipal4");
        principal.setType("type");
        principal.setDescription("description");
        principal.setRoles(roles);

        this.xaSecurityDao.saveOrUpdatePrincipal(principal);
        
        principal = new IkasanPrincipal();
        principal.setName("anotherPrincipal5");
        principal.setType("type");
        principal.setDescription("description");
        principal.setRoles(roles);

        this.xaSecurityDao.saveOrUpdatePrincipal(principal);
        
        principal = new IkasanPrincipal();
        principal.setName("anotherPrincipal6");
        principal.setType("type");
        principal.setDescription("description");
        principal.setRoles(roles);

        this.xaSecurityDao.saveOrUpdatePrincipal(principal);
        
        principal = new IkasanPrincipal();
        principal.setName("anotherPrincipal7");
        principal.setType("type");
        principal.setDescription("description");

        this.xaSecurityDao.saveOrUpdatePrincipal(principal);
        
        PolicyLinkType policyLinkType = new PolicyLinkType("name1", "table1");
        
        this.xaSecurityDao.saveOrUpdatePolicyLinkType(policyLinkType);
        
        policyLinkType = new PolicyLinkType("name2", "table2");
        
        this.xaSecurityDao.saveOrUpdatePolicyLinkType(policyLinkType);
        
        policyLinkType = new PolicyLinkType("name3", "table3");
        
        this.xaSecurityDao.saveOrUpdatePolicyLinkType(policyLinkType);
    }

    @Test(expected = IllegalArgumentException.class)
    @DirtiesContext
    public void test_exception_null_dao_on_construction() 
    {
        new SecurityServiceImpl(null);
    }

    @Test 
    @DirtiesContext
    public void test_success_get_principal_by_name() 
    {
        IkasanPrincipal principal = this.xaSecurityService.findPrincipalByName("stewmi");

        Assert.assertNotNull(principal);

        Assert.assertEquals(principal.getRoles().size(), 10);
    }

    @Test 
    @DirtiesContext
    public void test_success_create_new_principal() 
    {
        IkasanPrincipal principal = this.xaSecurityService.createNewPrincipal("stewmi2", "type");

        principal = this.xaSecurityService.findPrincipalByName("stewmi2");

        Assert.assertNotNull(principal);
    }

    @Test (expected = PropertyValueException.class)
    @DirtiesContext
    public void test_exception_create_new_principal_null_name() 
    {
        this.xaSecurityService.createNewPrincipal(null, "type");
    }

    @Test (expected = PropertyValueException.class)
    @DirtiesContext
    public void test_exception_create_new_principal_null_type() 
    {
        this.xaSecurityService.createNewPrincipal("name", null);
    }

    @Test 
    @DirtiesContext
    public void test_success_add_role()
    {
        IkasanPrincipal principal = this.xaSecurityDao.getPrincipalByName("stewmi");

        Assert.assertNotNull(principal);

        Assert.assertEquals(principal.getRoles().size(), 10);

        Role role = new Role();
        role.setName("role_new");
        role.setDescription("description");

        HashSet<Policy> policies = new HashSet<Policy>();

        for(int j=0; j<10; j++)
        {
            Policy policy = new Policy();
            policy.setName("policy" + j);
            policy.setDescription("description");
            this.xaSecurityDao.saveOrUpdatePolicy(policy);
            policies.add(policy);
        }

        role.setPolicies(policies);
        this.xaSecurityService.saveRole(role);

        principal.getRoles().add(role);

        this.xaSecurityDao.saveOrUpdatePrincipal(principal);

        principal = this.xaSecurityDao.getPrincipalByName("stewmi");

        Assert.assertNotNull(principal);

        Assert.assertEquals(principal.getRoles().size(), 11);
    }

    @Test 
    @DirtiesContext
    public void test_success_create_new_role() 
    {
        Role role = this.xaSecurityService.createNewRole("testRole", "description");

        Assert.assertNotNull(role);
    }

    @Test (expected = PropertyValueException.class)
    @DirtiesContext
    public void test_exception_create_new_role_null_name() 
    {
        this.xaSecurityService.createNewRole(null, "description");
    }

    @Test (expected = PropertyValueException.class)
    @DirtiesContext
    public void test_exception_create_new_role_null_description() 
    {
        this.xaSecurityService.createNewRole("role", null);
    }

    @Test 
    @DirtiesContext
    public void test_success_remove_role()
    {
        IkasanPrincipal principal = this.xaSecurityDao.getPrincipalByName("stewmi");

        Assert.assertNotNull(principal);

        Assert.assertEquals(principal.getRoles().size(), 10);

        Role role = new Role();
        role.setName("role_new");

        principal.getRoles().clear();

        this.xaSecurityService.savePrincipal(principal);

        principal = this.xaSecurityDao.getPrincipalByName("stewmi");

        Assert.assertNotNull(principal);

        Assert.assertEquals(principal.getRoles().size(), 0);
    }

    @Test(expected = PropertyValueException.class)
    @DirtiesContext
    public void test_exception_principal_no_name()
    {
        HashSet<Role> roles = new HashSet<Role>();
        HashSet<Policy> policies = new HashSet<Policy>();

        for(int i=0; i<10; i++)
        {
            Role role = new Role();
            role.setName("role-" + i);
            role.setDescription("description");

            for(int j=0; j<10; j++)
            {
                Policy policy = new Policy();
                policy.setName("policy-" + j + i);
                policy.setDescription("description");
                this.xaSecurityDao.saveOrUpdatePolicy(policy);
                policies.add(policy);
            }

            role.setPolicies(policies);
            this.xaSecurityDao.saveOrUpdateRole(role);
            roles.add(role);
            policies = new HashSet<Policy>();
        }

        IkasanPrincipal principal = new IkasanPrincipal();
        principal.setType("type");
        principal.setDescription("description");
        principal.setRoles(roles);

        this.xaSecurityService.savePrincipal(principal);
    }

    @Test(expected = ConstraintViolationException.class)
    @DirtiesContext
    public void test_exception_principal_duplicate_name() 
    {
        IkasanPrincipal principal = new IkasanPrincipal();
        principal.setName("anotherPrincipal7");
        principal.setDescription("description");
        principal.setType("type");

        this.xaSecurityService.savePrincipal(principal);
    }

    @Test
    @DirtiesContext
    public void test_get_all_principals() 
    {
        List<IkasanPrincipal> principals = this.xaSecurityService.getAllPrincipals();

        Assert.assertTrue(principals.size() == 8);
    }

    @Test
    @DirtiesContext
    public void test_delete_principal()
    {
        List<IkasanPrincipal> principals = this.xaSecurityDao.getAllPrincipals();

        Assert.assertTrue(principals.size() == 8);

        this.xaSecurityService.deletePrincipal(principals.get(0));

        principals = this.xaSecurityDao.getAllPrincipals();

        Assert.assertTrue(principals.size() == 7);
    }

    @Test(expected = ConstraintViolationException.class)
    @DirtiesContext
    public void test_exception_role_no_name()
    {
        HashSet<Role> roles = new HashSet<Role>();
        HashSet<Policy> policies = new HashSet<Policy>();

        for(int i=0; i<10; i++)
        {
            Role role = new Role();

            for(int j=0; j<10; j++)
            {
                Policy policy = new Policy();
                policy.setName("policy-" + j + i);
                policy.setDescription("description");
                this.xaSecurityDao.saveOrUpdatePolicy(policy);
                policies.add(policy);
            }

            role.setPolicies(policies);
            this.xaSecurityDao.saveOrUpdateRole(role);
            roles.add(role);
            policies = new HashSet<>();
        }

        IkasanPrincipal principal = new IkasanPrincipal();
        principal.setType("type");
        principal.setRoles(roles);

        this.xaSecurityService.savePrincipal(principal);
    }

    @Test
    @DirtiesContext
    public void test_get_all_roles() 
    {
        List<Role> roles = this.xaSecurityService.getAllRoles();

        Assert.assertTrue(roles.size() == 10);
    }

    @Test
    @DirtiesContext
    public void test_delete_role()
    {
        Role role = new Role();
        role.setName("role_new");
        role.setDescription("description");

        HashSet<Policy> policies = new HashSet<Policy>();

        for(int j=0; j<10; j++)
        {
            Policy policy = new Policy();
            policy.setName("policy" + j);
            policy.setDescription("description");
            this.xaSecurityDao.saveOrUpdatePolicy(policy);
            policies.add(policy);
        }

        role.setPolicies(policies);
        this.xaSecurityDao.saveOrUpdateRole(role);

        IkasanPrincipal principal = this.xaSecurityDao.getPrincipalByName("stewmi");
        principal.addRole(role);

        this.xaSecurityDao.saveOrUpdatePrincipal(principal);

        List<Role> roles = this.xaSecurityDao.getAllRoles();

        Assert.assertTrue(roles.size() == 11);

        role = this.xaSecurityService.getRoleById(role.getId());
        this.xaSecurityService.deleteRole(role);

        roles = this.xaSecurityDao.getAllRoles();

        Assert.assertTrue(roles.size() == 10);
    }

    @Test(expected = ConstraintViolationException.class)
    @DirtiesContext
    public void test_exception_principal_policy_name() 
    {
        Policy policy = new Policy();
        policy.setName("policy11");
        policy.setDescription("description");
        this.xaSecurityService.savePolicy(policy);
    }

    @Test(expected = PropertyValueException.class)
    @DirtiesContext
    public void test_exception_policy_no_name()
    {
        HashSet<Role> roles = new HashSet<Role>();
        HashSet<Policy> policies = new HashSet<Policy>();

        for(int i=0; i<10; i++)
        {
            Role role = new Role();
            role.setName("name");

            for(int j=0; j<10; j++)
            {
                Policy policy = new Policy();
                this.xaSecurityService.savePolicy(policy);
                policies.add(policy);
            }

            role.setPolicies(policies);
            this.xaSecurityDao.saveOrUpdateRole(role);
            roles.add(role);
            policies = new HashSet<Policy>();
        }

        IkasanPrincipal principal = new IkasanPrincipal();
        principal.setType("type");
        principal.setRoles(roles);

        this.xaSecurityService.savePrincipal(principal);
    }

    @Test 
    @DirtiesContext
    public void test_success_create_new_policy() 
    {
        Policy policy = this.xaSecurityService.createNewPolicy("testPolicy", "description");

        Assert.assertNotNull(policy);
    }

    @Test (expected = PropertyValueException.class)
    @DirtiesContext
    public void test_exception_create_new_policy_null_name() 
    {
        this.xaSecurityService.createNewPolicy(null, "description");
    }

    @Test (expected = PropertyValueException.class)
    @DirtiesContext
    public void test_exception_create_new_policy_null_description() 
    {
        this.xaSecurityService.createNewPolicy("role", null);
    }

    @Test(expected = ConstraintViolationException.class)
    @DirtiesContext
    public void test_exception_role_name() 
    {
        Role role = new Role();
        role.setName("role1");

        this.xaSecurityService.saveRole(role);
    }

    @Test
    @DirtiesContext
    public void test_get_all_policies() 
    {
        List<Policy> policies = this.xaSecurityService.getAllPolicies();

        Assert.assertTrue(policies.size() == 100);
    }

    @Test
    @DirtiesContext
    public void test_delete_policy() 
    {
        Policy policy = new Policy();
        policy.setName("blah");
        policy.setDescription("description");
        this.xaSecurityService.savePolicy(policy);

        List<Policy> policies = this.xaSecurityDao.getAllPolicies();

        Assert.assertTrue(policies.size() == 101);

        this.xaSecurityService.deletePolicy(policy);

        policies = this.xaSecurityDao.getAllPolicies();

        Assert.assertTrue(policies.size() == 100);
    }
    
    @Test
    @DirtiesContext
    public void test_success_get_principal_with_role()
    {
    	List<IkasanPrincipal> principals = this.xaSecurityService.getAllPrincipalsWithRole("role0");
    	
    	Assert.assertTrue(principals.size() == 7);
    }
    
    @Test
    @DirtiesContext
    public void test_success_get_principal_with_role_bad_role_name()
    {
    	List<IkasanPrincipal> principals = this.xaSecurityService.getAllPrincipalsWithRole("bad name");
    	
    	Assert.assertTrue(principals.size() == 0);
    }
    
    @Test
    @DirtiesContext
    public void test_success_get_principal_by_name_like()
    {    	
    	List<IkasanPrincipal> principals = this.xaSecurityService.getPrincipalByNameLike("anotherPrincipal");

    	Assert.assertTrue(principals.size() == 7);
    }
    
    @Test
    @DirtiesContext
    public void test_success_get_principal_by_name_like_bad_bname()
    {    	
    	List<IkasanPrincipal> principals = this.xaSecurityService.getPrincipalByNameLike("bad name");

    	Assert.assertTrue(principals.size() == 0);
    }
    
    @Test
    @DirtiesContext
    public void test_success_get_all_policy_link_types()
    {    	
    	List<PolicyLinkType> plts = this.xaSecurityService.getAllPolicyLinkTypes();

    	Assert.assertTrue(plts.size() == 3);
    }
 
    @Test
    @DirtiesContext
    public void test_success_get_role_by_name_like()
    {    	
    	List<Role> roles = this.xaSecurityService.getRoleByNameLike("role");

    	Assert.assertTrue(roles.size() == 10);
    }
 
    @Test
    @DirtiesContext
    public void test_success_get_policy_by_name_like()
    {    	
    	List<Policy> policies = this.xaSecurityService.getPolicyByNameLike("policy");

    	Assert.assertTrue(policies.size() == 100);
    }
    
    @Test
    @DirtiesContext
    public void test_success_save_policy_link()
    {    	
    	List<PolicyLinkType> plts = this.xaSecurityService.getAllPolicyLinkTypes();
    	PolicyLink policyLink = new PolicyLink(plts.get(0),Long.valueOf(1), "name");
    	
    	this.xaSecurityService.savePolicyLink(policyLink);
    	
    	Assert.assertNotNull(policyLink.getId());
    	
    	this.xaSecurityService.deletePolicyLink(policyLink);
    }
    
    @Test
    @DirtiesContext
    public void test_success_get_policies_by_role()
    {    	
    	List<Policy> policies = this.xaSecurityService.getAllPoliciesWithRole("role1");

    	Assert.assertEquals(10,policies.size());
    }
    
    @Test
    @DirtiesContext
    public void test_success_get_principal_by_names()
    {
    	ArrayList<String> names = new ArrayList<String>();
    	names.add("role1");
    	names.add("role2");
    	List<IkasanPrincipal> principals = this.xaSecurityService.getPrincipalsByName(names);

    	Assert.assertTrue(principals.size() == 7);
    }
    
    @Test
    @DirtiesContext
    public void test_success_get_principal_by_names_empty_names()
    {
    	ArrayList<String> names = new ArrayList<String>();

    	List<IkasanPrincipal> principals = this.xaSecurityService.getPrincipalsByName(names);
    	
    	Assert.assertTrue(principals.size() == 0);
    }

    @Test 
    @DirtiesContext
    public void test_success_get_policy_by_name()
    {
        Policy policy = this.xaSecurityService.findPolicyByName("policy11");

        Assert.assertNotNull(policy);

        Assert.assertEquals(policy.getName(), "policy11");
    }

    @Test 
    @DirtiesContext
    public void test_success_get_role_by_name()
    {
        Role role = this.xaSecurityService.findRoleByName("role1");

        Assert.assertNotNull(role);

        Assert.assertEquals(role.getName(), "role1");
    }

    @Test
    @DirtiesContext
    @Ignore //todo need to revisit this test
    public void test_success_add_all_policies_to_role_with_different_policy()
    {
        Role role = this.xaSecurityService.findRoleByName("role1");

        List<Policy> policies = this.xaSecurityService.getAllPolicies();
        policies.forEach(policy -> role.addPolicy(policy));
        this.xaSecurityService.saveRole(role);

        Role role2 = this.xaSecurityService.findRoleByName("role2");

        policies.forEach(policy -> role2.addPolicy(policy));
        this.xaSecurityService.saveRole(role2);

        // load the policies again so that there are 2 objects
        // in the session that reference the same row in the DB.
        List<Policy> policies2 = this.xaSecurityService.getAllPolicies();
        policies2.forEach(policy -> role.addPolicy(policy));

        this.xaSecurityService.saveRole(role);
    }

    @Test
    @DirtiesContext
    public void test_success_confirm_deleting_role_does_not_delete_policies()
    {
        Role role = this.xaSecurityService.findRoleByName("role1");

        List<Policy> policies = this.xaSecurityService.getAllPolicies();
        policies.forEach(policy -> role.addPolicy(policy));
        this.xaSecurityService.saveRole(role);

        List<IkasanPrincipal> principals = this.xaSecurityService.getAllPrincipalsWithRole(role.getName());

        principals.forEach(principal -> {
            principal.getRoles().remove(role);
            this.xaSecurityService.savePrincipal(principal);
        });

        this.xaSecurityService.deleteRole(role);

        List<Policy> policies2 = this.xaSecurityService.getAllPolicies();

        // Make sure deleting roles does not delete policies too.
        Assert.assertEquals(policies.size(), policies2.size());
    }

    @Test
    @DirtiesContext
    public void test_complex_add_policy_to_role()
    {
        Role role = new Role();
        role.setName("role_new");
        role.setDescription("description");

        HashSet<Policy> policies = new HashSet<>();

        for(int j=0; j<10; j++)
        {
            Policy policy = new Policy();
            policy.setName("policy" + j);
            policy.setDescription("description");
            this.xaSecurityService.savePolicy(policy);
            policies.add(policy);
        }

        // insert the role into the db
        this.xaSecurityService.saveRole(role);

        // add a policy to the role and save the role
        List<Policy> dbPolicies = this.xaSecurityService.getAllPolicies();
        role.addPolicy(dbPolicies.get(0));
        this.xaSecurityService.saveRole(role);

        Assert.assertEquals(1, role.getPolicies().size());

        // add another policy to the role along with the existing and save the role
        role.addPolicy(dbPolicies.get(0));
        role.addPolicy(dbPolicies.get(1));

        // save the role
        this.xaSecurityService.saveRole(role);
        // make sure there are only 2 policies associated with the role
        Assert.assertEquals(2, role.getPolicies().size());

        // refresh the role from the DB
        role = this.xaSecurityService.getAllRoles().stream()
            .filter(role1 -> role1.getName().equals("role_new"))
            .collect(Collectors.toList()).get(0);

        // make sure there are still only 2 policies associated with the role
        Assert.assertEquals(2, role.getPolicies().size());

        // now add some more policies including ones that are already associated
        // with the role
        role.addPolicy(dbPolicies.get(0));
        role.addPolicy(dbPolicies.get(0));
        role.addPolicy(dbPolicies.get(1));
        role.addPolicy(dbPolicies.get(2));

        // save the role.
        this.xaSecurityService.saveRole(role);
        Assert.assertEquals(3, role.getPolicies().size());

        // get the role from the db again.
        role = this.xaSecurityService.getAllRoles().stream()
            .filter(role1 -> role1.getName().equals("role_new"))
            .collect(Collectors.toList()).get(0);

        // make sure we have the expected number of policies on the role
        Assert.assertEquals(3, role.getPolicies().size());

        // now we'll remove a policy from a role
        Policy policy = role.getPolicies().stream().findFirst().get();
        role.getPolicies().remove(policy);

        // save the role
        this.xaSecurityService.saveRole(role);
        // confirm the number of policies
        Assert.assertEquals(2, role.getPolicies().size());

        // get the role from the db again.
        role = this.xaSecurityService.getAllRoles().stream()
            .filter(role1 -> role1.getName().equals("role_new"))
            .collect(Collectors.toList()).get(0);

        // make sure we have the expected number of policies on the role
        Assert.assertEquals(2, role.getPolicies().size());
    }

    @Test
    @DirtiesContext
    public void test_success_add_role_wth_role_job_plan_then_reassign_roles_to_the_job_plan()
    {
        IkasanPrincipal principal = this.xaSecurityDao.getPrincipalByName("stewmi");

        Assert.assertNotNull(principal);

        Assert.assertEquals(principal.getRoles().size(), 10);

        Role role = new Role();
        role.setName("role_new");
        role.setDescription("description");

        HashSet<Policy> policies = new HashSet<>();

        for(int j=0; j<10; j++)
        {
            Policy policy = new Policy();
            policy.setName("policy" + j);
            policy.setDescription("description");
            this.xaSecurityDao.saveOrUpdatePolicy(policy);
            policies.add(policy);
        }


        role.setPolicies(policies);
        this.xaSecurityDao.saveOrUpdateRole(role);

        RoleJobPlan roleJobPlan = new RoleJobPlan();
        roleJobPlan.setJobPlanName("jobPlan");
        roleJobPlan.setRole(role);
        this.xaSecurityDao.saveRoleJobPlan(roleJobPlan);

        role.addRoleJobPlan(roleJobPlan);
        this.xaSecurityDao.saveOrUpdateRole(role);

        principal.getRoles().add(role);

        this.xaSecurityDao.saveOrUpdatePrincipal(principal);

        principal = this.xaSecurityDao.getPrincipalByName("stewmi");

        Assert.assertNotNull(principal);

        Assert.assertEquals(principal.getRoles().size(), 11);

        Role foundRole = principal.getRoles().stream().filter(role1 -> role1.getName().equals("role_new")).findFirst().get();

        RoleJobPlan foundRoleJobPlan = foundRole.getRoleJobPlans().stream().findFirst().get();

        Assert.assertEquals("found role module equals", roleJobPlan.getJobPlanName(), foundRoleJobPlan.getJobPlanName());

        this.xaSecurityService.setJobPlanRoles("jobPlan", List.of("role0", "role3", "role7", "unknown_role"));

        List<RoleJobPlan> roleJobPlans = this.xaSecurityDao.getRoleJobPlansByJobPlanName("jobPlan");

        Assert.assertEquals(3, roleJobPlans.size());
        Assert.assertEquals("role0", roleJobPlans.get(0).getRole().getName());
        Assert.assertEquals("role3", roleJobPlans.get(1).getRole().getName());
        Assert.assertEquals("role7", roleJobPlans.get(2).getRole().getName());
    }

    @Test
    @DirtiesContext
    public void test_get_principal_count_with_filter()
    {
        IkasanPrincipalFilter filter = new IkasanPrincipalFilter();

        int count = this.xaSecurityService.getPrincipalCount(filter);
        Assert.assertEquals(count, 8);

        // test name filter
        filter.setNameFilter("ewm");
        count = this.xaSecurityService.getPrincipalCount(filter);
        Assert.assertEquals(count, 1);

        // test type filter
        filter = new IkasanPrincipalFilter();
        filter.setTypeFilter("type");
        count = this.xaSecurityService.getPrincipalCount(filter);
        Assert.assertEquals(count, 8);

        // test description filter
        filter = new IkasanPrincipalFilter();
        filter.setDescriptionFilter("desc");
        count = this.xaSecurityService.getPrincipalCount(filter);
        Assert.assertEquals(count, 8);

        // test aggregate filter
        filter = new IkasanPrincipalFilter();
        filter.setNameFilter("ewm");
        filter.setTypeFilter("type");
        filter.setDescriptionFilter("ript");
        count = this.xaSecurityService.getPrincipalCount(filter);
        Assert.assertEquals(count, 1);

        // set sorting
        filter = new IkasanPrincipalFilter();
        filter.setSortColumn("name");
        filter.setSortOrder("ASCENDING");
        count = this.xaSecurityService.getPrincipalCount(filter);
        Assert.assertEquals(count, 8);

        filter.setSortOrder("DESCENDING");
        count = this.xaSecurityService.getPrincipalCount(filter);
        Assert.assertEquals(count, 8);
    }

    @Test
    @DirtiesContext
    public void test_get_principal_lites_with_filter_limit_and_offset()
    {
        IkasanPrincipalFilter filter = new IkasanPrincipalFilter();

        List<IkasanPrincipalLite> principals = this.xaSecurityService.getPrincipalLites(filter, 100, 0);
        Assert.assertTrue(principals.size() == 8);

        // test offset
        principals = this.xaSecurityService.getPrincipalLites(filter, 100, 2);
        Assert.assertTrue(principals.size() == 6);

        // test limit
        principals = this.xaSecurityService.getPrincipalLites(filter, 2, 0);
        Assert.assertTrue(principals.size() == 2);

        // test limit and offset
        principals = this.xaSecurityService.getPrincipalLites(filter, 2, 2);
        Assert.assertTrue(principals.size() == 2);
        Assert.assertEquals("anotherPrincipal2", principals.get(0).getName());
        Assert.assertEquals("anotherPrincipal3", principals.get(1).getName());

        // test name filter
        filter.setNameFilter("ewm");
        principals = this.xaSecurityService.getPrincipalLites(filter, 100, 0);
        Assert.assertTrue(principals.size() == 1);
        Assert.assertEquals("stewmi", principals.get(0).getName());

        // test type filter
        filter = new IkasanPrincipalFilter();
        filter.setTypeFilter("type");
        principals = this.xaSecurityService.getPrincipalLites(filter, 100, 0);
        Assert.assertTrue(principals.size() == 8);

        // test description filter
        filter = new IkasanPrincipalFilter();
        filter.setDescriptionFilter("desc");
        principals = this.xaSecurityService.getPrincipalLites(filter, 100, 0);
        Assert.assertTrue(principals.size() == 8);

        // test aggregate filter
        filter = new IkasanPrincipalFilter();
        filter.setNameFilter("ewm");
        filter.setTypeFilter("type");
        filter.setDescriptionFilter("ript");
        principals = this.xaSecurityService.getPrincipalLites(filter, 100, 0);
        Assert.assertTrue(principals.size() == 1);
        Assert.assertEquals("stewmi", principals.get(0).getName());

        // set sorting
        filter = new IkasanPrincipalFilter();
        filter.setSortColumn("name");
        filter.setSortOrder("ASCENDING");
        principals = this.xaSecurityService.getPrincipalLites(filter, 100, 0);
        Assert.assertTrue(principals.size() == 8);
        Assert.assertEquals("anotherPrincipal1", principals.get(0).getName());
        Assert.assertEquals("anotherPrincipal2", principals.get(1).getName());
        Assert.assertEquals("anotherPrincipal3", principals.get(2).getName());
        Assert.assertEquals("anotherPrincipal4", principals.get(3).getName());
        Assert.assertEquals("anotherPrincipal5", principals.get(4).getName());
        Assert.assertEquals("anotherPrincipal6", principals.get(5).getName());
        Assert.assertEquals("anotherPrincipal7", principals.get(6).getName());
        Assert.assertEquals("stewmi", principals.get(7).getName());

        filter.setSortOrder("DESCENDING");
        principals = this.xaSecurityService.getPrincipalLites(filter, 100, 0);
        Assert.assertTrue(principals.size() == 8);
        Assert.assertEquals("anotherPrincipal1", principals.get(7).getName());
        Assert.assertEquals("anotherPrincipal2", principals.get(6).getName());
        Assert.assertEquals("anotherPrincipal3", principals.get(5).getName());
        Assert.assertEquals("anotherPrincipal4", principals.get(4).getName());
        Assert.assertEquals("anotherPrincipal5", principals.get(3).getName());
        Assert.assertEquals("anotherPrincipal6", principals.get(2).getName());
        Assert.assertEquals("anotherPrincipal7", principals.get(1).getName());
        Assert.assertEquals("stewmi", principals.get(0).getName());
    }

    @Test
    @DirtiesContext
    public void test_get_principals_with_filter_limit_and_offset()
    {
        IkasanPrincipalFilter filter = new IkasanPrincipalFilter();

        List<IkasanPrincipal> principals = this.xaSecurityService.getPrincipals(filter, 100, 0);
        Assert.assertTrue(principals.size() == 8);

        // test offset
        principals = this.xaSecurityService.getPrincipals(filter, 100, 2);
        Assert.assertTrue(principals.size() == 6);

        // test limit
        principals = this.xaSecurityService.getPrincipals(filter, 2, 0);
        Assert.assertTrue(principals.size() == 2);

        // test limit and offset
        principals = this.xaSecurityService.getPrincipals(filter, 2, 2);
        Assert.assertTrue(principals.size() == 2);
        Assert.assertEquals("anotherPrincipal2", principals.get(0).getName());
        Assert.assertEquals("anotherPrincipal3", principals.get(1).getName());

        // test name filter
        filter.setNameFilter("ewm");
        principals = this.xaSecurityService.getPrincipals(filter, 100, 0);
        Assert.assertTrue(principals.size() == 1);
        Assert.assertEquals("stewmi", principals.get(0).getName());

        // test type filter
        filter = new IkasanPrincipalFilter();
        filter.setTypeFilter("type");
        principals = this.xaSecurityService.getPrincipals(filter, 100, 0);
        Assert.assertTrue(principals.size() == 8);

        // test description filter
        filter = new IkasanPrincipalFilter();
        filter.setDescriptionFilter("desc");
        principals = this.xaSecurityService.getPrincipals(filter, 100, 0);
        Assert.assertTrue(principals.size() == 8);

        // test aggregate filter
        filter = new IkasanPrincipalFilter();
        filter.setNameFilter("ewm");
        filter.setTypeFilter("type");
        filter.setDescriptionFilter("ript");
        principals = this.xaSecurityService.getPrincipals(filter, 100, 0);
        Assert.assertTrue(principals.size() == 1);
        Assert.assertEquals("stewmi", principals.get(0).getName());

        // set sorting
        filter = new IkasanPrincipalFilter();
        filter.setSortColumn("name");
        filter.setSortOrder("ASCENDING");
        principals = this.xaSecurityService.getPrincipals(filter, 100, 0);
        Assert.assertTrue(principals.size() == 8);
        Assert.assertEquals("anotherPrincipal1", principals.get(0).getName());
        Assert.assertEquals("anotherPrincipal2", principals.get(1).getName());
        Assert.assertEquals("anotherPrincipal3", principals.get(2).getName());
        Assert.assertEquals("anotherPrincipal4", principals.get(3).getName());
        Assert.assertEquals("anotherPrincipal5", principals.get(4).getName());
        Assert.assertEquals("anotherPrincipal6", principals.get(5).getName());
        Assert.assertEquals("anotherPrincipal7", principals.get(6).getName());
        Assert.assertEquals("stewmi", principals.get(7).getName());

        filter.setSortOrder("DESCENDING");
        principals = this.xaSecurityService.getPrincipals(filter, 100, 0);
        Assert.assertTrue(principals.size() == 8);
        Assert.assertEquals("anotherPrincipal1", principals.get(7).getName());
        Assert.assertEquals("anotherPrincipal2", principals.get(6).getName());
        Assert.assertEquals("anotherPrincipal3", principals.get(5).getName());
        Assert.assertEquals("anotherPrincipal4", principals.get(4).getName());
        Assert.assertEquals("anotherPrincipal5", principals.get(3).getName());
        Assert.assertEquals("anotherPrincipal6", principals.get(2).getName());
        Assert.assertEquals("anotherPrincipal7", principals.get(1).getName());
        Assert.assertEquals("stewmi", principals.get(0).getName());
    }

    @Test
    @DirtiesContext
    public void test_get_principals_with_role_with_filter_limit_and_offset()
    {
        IkasanPrincipalFilter filter = new IkasanPrincipalFilter();

        List<IkasanPrincipalLite> principals = this.xaSecurityService.getAllPrincipalsWithRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(7, principals.size());

        // test offset
        principals = this.xaSecurityService.getAllPrincipalsWithRole
            ("role1", filter, 100, 2);
        Assert.assertEquals(5, principals.size());

        // test limit
        principals = this.xaSecurityService.getAllPrincipalsWithRole
            ("role1", filter, 2, 0);
        Assert.assertEquals(2, principals.size());

        // test limit and offset
        principals = this.xaSecurityService.getAllPrincipalsWithRole
            ("role1", filter, 2, 2);
        Assert.assertEquals(2, principals.size());
        Assert.assertEquals("anotherPrincipal2", principals.get(0).getName());
        Assert.assertEquals("anotherPrincipal3", principals.get(1).getName());

        // test name filter
        filter.setNameFilter("ewm");
        principals = this.xaSecurityService.getAllPrincipalsWithRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(1, principals.size());

        // test type filter
        filter = new IkasanPrincipalFilter();
        filter.setTypeFilter("type");
        principals = this.xaSecurityService.getAllPrincipalsWithRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(7, principals.size());

        // test description filter
        filter = new IkasanPrincipalFilter();
        filter.setDescriptionFilter("desc");
        principals = this.xaSecurityService.getAllPrincipalsWithRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(7, principals.size());

        // test aggregate filter
        filter = new IkasanPrincipalFilter();
        filter.setNameFilter("ewm");
        filter.setTypeFilter("type");
        filter.setDescriptionFilter("ript");
        principals = this.xaSecurityService.getAllPrincipalsWithRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(1, principals.size());

        // set sorting
        filter = new IkasanPrincipalFilter();
        filter.setSortColumn("name");
        filter.setSortOrder("ASCENDING");
        principals = this.xaSecurityService.getAllPrincipalsWithRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(7, principals.size());
        Assert.assertEquals("anotherPrincipal1", principals.get(0).getName());
        Assert.assertEquals("anotherPrincipal2", principals.get(1).getName());
        Assert.assertEquals("anotherPrincipal3", principals.get(2).getName());
        Assert.assertEquals("anotherPrincipal4", principals.get(3).getName());
        Assert.assertEquals("anotherPrincipal5", principals.get(4).getName());
        Assert.assertEquals("anotherPrincipal6", principals.get(5).getName());
        Assert.assertEquals("stewmi", principals.get(6).getName());

        filter.setSortOrder("DESCENDING");
        principals = this.xaSecurityService.getAllPrincipalsWithRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(7, principals.size());
        Assert.assertEquals("anotherPrincipal1", principals.get(6).getName());
        Assert.assertEquals("anotherPrincipal2", principals.get(5).getName());
        Assert.assertEquals("anotherPrincipal3", principals.get(4).getName());
        Assert.assertEquals("anotherPrincipal4", principals.get(3).getName());
        Assert.assertEquals("anotherPrincipal5", principals.get(2).getName());
        Assert.assertEquals("anotherPrincipal6", principals.get(1).getName());
        Assert.assertEquals("stewmi", principals.get(0).getName());
    }

    @Test
    @DirtiesContext
    public void test_get_principals_with_role_count_with_filter_limit_and_offset()
    {
        IkasanPrincipalFilter filter = new IkasanPrincipalFilter();

        int principals = this.xaSecurityService.getPrincipalsWithRoleCount
            ("role1", filter);
        Assert.assertEquals(7, principals);

        // test name filter
        filter.setNameFilter("ewm");
        principals = this.xaSecurityService.getPrincipalsWithRoleCount
            ("role1", filter);
        Assert.assertEquals(1, principals);

        // test type filter
        filter = new IkasanPrincipalFilter();
        filter.setTypeFilter("type");
        principals = this.xaSecurityService.getPrincipalsWithRoleCount
            ("role1", filter);
        Assert.assertEquals(7, principals);

        // test description filter
        filter = new IkasanPrincipalFilter();
        filter.setDescriptionFilter("desc");
        principals = this.xaSecurityService.getPrincipalsWithRoleCount
            ("role1", filter);
        Assert.assertEquals(7, principals);

        // test aggregate filter
        filter = new IkasanPrincipalFilter();
        filter.setNameFilter("ewm");
        filter.setTypeFilter("type");
        filter.setDescriptionFilter("ript");
        principals = this.xaSecurityService.getPrincipalsWithRoleCount
            ("role1", filter);
        Assert.assertEquals(1, principals);

        // set sorting
        filter = new IkasanPrincipalFilter();
        filter.setSortColumn("name");
        filter.setSortOrder("ASCENDING");
        principals = this.xaSecurityService.getPrincipalsWithRoleCount
            ("role1", filter);
        Assert.assertEquals(7, principals);

        filter.setSortOrder("DESCENDING");
        principals = this.xaSecurityService.getPrincipalsWithRoleCount
            ("role1", filter);
        Assert.assertEquals(7, principals);
    }

    @Test
    @DirtiesContext
    public void test_get_principals_without_role_with_filter_limit_and_offset()
    {
        IkasanPrincipalFilter filter = new IkasanPrincipalFilter();

        List<IkasanPrincipalLite> principals = this.xaSecurityService.getAllPrincipalsWithoutRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(1, principals.size());

        // test offset
        principals = this.xaSecurityService.getAllPrincipalsWithoutRole
            ("role1", filter, 100, 2);
        Assert.assertEquals(0, principals.size());

        // test limit
        principals = this.xaSecurityService.getAllPrincipalsWithoutRole
            ("role1", filter, 2, 0);
        Assert.assertEquals(1, principals.size());
        Assert.assertEquals("anotherPrincipal7", principals.get(0).getName());

        // test limit and offset
        principals = this.xaSecurityService.getAllPrincipalsWithoutRole
            ("role1", filter, 2, 2);
        Assert.assertEquals(0, principals.size());

        // test name filter
        filter.setNameFilter("ewm");
        principals = this.xaSecurityService.getAllPrincipalsWithoutRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(0, principals.size());

        filter.setNameFilter("other");
        principals = this.xaSecurityService.getAllPrincipalsWithoutRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(1, principals.size());

        // test type filter
        filter = new IkasanPrincipalFilter();
        filter.setTypeFilter("type");
        principals = this.xaSecurityService.getAllPrincipalsWithoutRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(1, principals.size());

        // test description filter
        filter = new IkasanPrincipalFilter();
        filter.setDescriptionFilter("desc");
        principals = this.xaSecurityService.getAllPrincipalsWithoutRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(1, principals.size());

        // test aggregate filter
        filter = new IkasanPrincipalFilter();
        filter.setNameFilter("other");
        filter.setTypeFilter("type");
        filter.setDescriptionFilter("ript");
        principals = this.xaSecurityService.getAllPrincipalsWithoutRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(1, principals.size());

        // set sorting
        filter = new IkasanPrincipalFilter();
        filter.setSortColumn("name");
        filter.setSortOrder("ASCENDING");
        principals = this.xaSecurityService.getAllPrincipalsWithoutRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(1, principals.size());
        Assert.assertEquals("anotherPrincipal7", principals.get(0).getName());

        filter.setSortOrder("DESCENDING");
        principals = this.xaSecurityService.getAllPrincipalsWithoutRole
            ("role1", filter, 100, 0);
        Assert.assertEquals(1, principals.size());
        Assert.assertEquals("anotherPrincipal7", principals.get(0).getName());
    }

    @Test
    @DirtiesContext
    public void test_get_principals_without_role_count_with_filter_limit_and_offset() {
        IkasanPrincipalFilter filter = new IkasanPrincipalFilter();

        int principals = this.xaSecurityService.getPrincipalsWithoutRoleCount
            ("role1", filter);
        Assert.assertEquals(1, principals);

        // test name filter
        filter.setNameFilter("ewm");
        principals = this.xaSecurityService.getPrincipalsWithoutRoleCount
            ("role1", filter);
        Assert.assertEquals(0, principals);

        filter.setNameFilter("other");
        principals = this.xaSecurityService.getPrincipalsWithoutRoleCount
            ("role1", filter);
        Assert.assertEquals(1, principals);

        // test type filter
        filter = new IkasanPrincipalFilter();
        filter.setTypeFilter("type");
        principals = this.xaSecurityService.getPrincipalsWithoutRoleCount
            ("role1", filter);
        Assert.assertEquals(1, principals);

        // test description filter
        filter = new IkasanPrincipalFilter();
        filter.setDescriptionFilter("desc");
        principals = this.xaSecurityService.getPrincipalsWithoutRoleCount
            ("role1", filter);
        Assert.assertEquals(1, principals);

        // test aggregate filter
        filter = new IkasanPrincipalFilter();
        filter.setNameFilter("other");
        filter.setTypeFilter("type");
        filter.setDescriptionFilter("ript");
        principals = this.xaSecurityService.getPrincipalsWithoutRoleCount
            ("role1", filter);
        Assert.assertEquals(1, principals);

        // set sorting
        filter = new IkasanPrincipalFilter();
        filter.setSortColumn("name");
        filter.setSortOrder("ASCENDING");
        principals = this.xaSecurityService.getPrincipalsWithoutRoleCount
            ("role1", filter);
        Assert.assertEquals(1, principals);

        filter.setSortOrder("DESCENDING");
        principals = this.xaSecurityService.getPrincipalsWithoutRoleCount
            ("role1", filter);
        Assert.assertEquals(1, principals);
    }
}
