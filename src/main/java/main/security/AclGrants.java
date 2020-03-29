package main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import main.domain.Book;

@Transactional
public class AclGrants {
	private final JdbcMutableAclService aclService;

	@Autowired
	public AclGrants(AclService aclService) {
		this.aclService = (JdbcMutableAclService) aclService;
	}
	public void addAclBook(Book b) {
		final Sid owner = new PrincipalSid(SecurityContextHolder.getContext().getAuthentication());
		final Sid admin = new GrantedAuthoritySid("ROLE_ADMIN");
		final Sid user = new GrantedAuthoritySid("ROLE_USER");

		final ObjectIdentity oid = new ObjectIdentityImpl(Book.class, b.getId());
		final MutableAcl acl = aclService.createAcl(oid);
		acl.setOwner(owner);
		acl.insertAce(acl.getEntries().size(), BasePermission.READ, owner, true);
		acl.insertAce(acl.getEntries().size(), BasePermission.READ, admin, true);
		acl.insertAce(acl.getEntries().size(), BasePermission.READ, user, true);
		acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, owner, true);
		acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, admin, true);
		aclService.updateAcl(acl);
	}
}

