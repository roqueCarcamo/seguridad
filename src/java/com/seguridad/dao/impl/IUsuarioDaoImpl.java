package com.seguridad.dao.impl;

import com.seguridad.controller.LoginUsuarioBean;
import com.seguridad.dao.IUsuarioDao;
import com.seguridad.model.Usuario;
import com.seguridad.security.md5hash;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rodolfo
 */
@Stateless
public class IUsuarioDaoImpl implements IUsuarioDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(rollbackForClassName = "java.lang.Exception")
    @Override
    public void insert(Usuario usuario) throws Exception {
        usuario.setPassword(md5hash.sha1(usuario.getPassword()));
        entityManager.persist(usuario);
    }

    @Override
    public Usuario iniciarSesion(Usuario usuario) throws Exception {
        Usuario user;
        user = new Usuario();
        Query q = entityManager.createNativeQuery("select u.id_tusuario, u.nombres, u.apellidos, u.cuenta, u.password from TUSUARIO u where cuenta = ? and password = ? ");
        q.setParameter(1, usuario.getCuenta());
        q.setParameter(2, usuario.getPassword());

        Object[] userVal = (Object[]) q.getSingleResult();
        if (userVal[0] != null) {
            user.setIdUsuario(Integer.parseInt(userVal[0].toString()));
        }
        if (userVal[1] != null) {
            user.setNombres(userVal[1].toString());
        }
        if (userVal[2] != null) {
            user.setApellidos(userVal[2].toString());
        }
        if (userVal[3] != null) {
            user.setCuenta(userVal[3].toString());
        }
        if (userVal[4] != null) {
            user.setPassword(userVal[4].toString());
        }
        return user;
    }
    
    @Override
    public Usuario getKeyPrivate(Usuario usuario) throws Exception {
        Usuario user;
        user = new Usuario();
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        LoginUsuarioBean loginBean = (LoginUsuarioBean) request.getSession().getAttribute("loginUsuarioBean");
        
        Query q = entityManager.createNativeQuery("select keyprivate from TUSUARIO u where keyprivate = ?");
        q.setParameter(1, loginBean.getUsuario().getIdUsuario());
        
        Object keyPrivateVal = (Object) q.getSingleResult();
        if (keyPrivateVal != null) {
            
           // user.setKeyprivate(keyPrivateVal);
             
        }
        //BDconexion.getConnection();
        return user;
    }

    @Transactional(rollbackForClassName = "java.lang.Exception")
    @Override
    public List<Usuario> listAll() throws Exception {
        List<Usuario> listaUsuarios = new ArrayList<>();
        Query q = entityManager.createNativeQuery("select u.id_tusuario, u.nombres, u.apellidos, u.cuenta, u.password from TUSUARIO u ");
        List<Object[]> lista = q.getResultList();
        Usuario usuario;
        for (int i = 0; i < lista.size(); i++) {
            Object[] usua = lista.get(i);
            usuario = new Usuario();
            if (usua[0] != null) {
                usuario.setIdUsuario(Integer.parseInt(usua[0].toString()));
            }
            if (usua[1] != null) {
                usuario.setNombres(usua[1].toString());
            }
            if (usua[2] != null) {
                usuario.setApellidos(usua[2].toString());
            }
            if (usua[3] != null) {
                usuario.setCuenta(usua[3].toString());
            }
            if (usua[4] != null) {
                usuario.setPassword(usua[4].toString());
            }
            listaUsuarios.add(usuario);
        }
        return listaUsuarios;
    }

}
