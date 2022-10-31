/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.*;

/**
 *
 * @author 14686
 */
public class UserServlet extends HttpServlet {

    private boolean first = true;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> users;
        List<Role> roles;

        UserService us = new UserService();
        RoleService rs = new RoleService();

        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        try {
            users = us.getAll();
            roles = rs.getAll();
            request.setAttribute("users", users);
            request.setAttribute("roles", roles);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);

        if (action.equals("edit")) {
            //need more code here
        } else if (action.equals("delete")) {
            String deleteEmail = request.getParameter("user.getEmail");
            try {
                us.delete(deleteEmail);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                users = us.getAll();
                roles = rs.getAll();
                request.setAttribute("users", users);
                request.setAttribute("roles", roles);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> users;
        List<Role> roles;

        String action = request.getParameter("action");
        UserService us = new UserService();
        RoleService rs = new RoleService();

        request.setAttribute("edit", false);

        HttpSession session = request.getSession();
        if (action != null && action.equals("add")) {

            String email = request.getParameter("email");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String password = request.getParameter("password");
            String roleName = request.getParameter("role");
            int roleId;
            if (roleName.equals("regular user")) {
                roleId = 2;
            } else {
                roleId = 1;
            }

            try {
                us.insert(new User(email, firstName, lastName, password, new Role(roleId, "")));
                request.removeAttribute("error");
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            users = us.getAll();
            roles = rs.getAll();

            request.setAttribute("users", users);
            request.setAttribute("roles", roles);

        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        getServletContext().getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);

    }

}
