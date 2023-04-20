package com.emergentes.controlador;

import com.emergentes.modelo.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String op = request.getParameter("op");
        String opcion = ( op == null) ? "view" : op;
        HttpSession ses = request.getSession();

        if (ses.getAttribute("listaper") == null) {
            ArrayList<Persona> listaux = new ArrayList<Persona>();
            ses.setAttribute("listaper", listaux);
        }
        ArrayList<Persona> lista = (ArrayList<Persona>)ses.getAttribute("listaper");
        
        Persona obj1 = new Persona();
        int id, pos;
        switch(opcion){
            case "nuevo": //Inserta nuevo registro
                
                request.setAttribute("miPersona", obj1);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "editar": //Modificar registro
                id = Integer.parseInt(request.getParameter("id"));
                pos = buscarIndice(request, id);
                obj1 = lista.get(pos);
                request.setAttribute("miPersona",obj1);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "eliminar": //Eliminar registro
                pos = buscarIndice(request, Integer.parseInt(request.getParameter("id")));
                lista.remove(pos);
                ses.setAttribute("listaper", lista);
                response.sendRedirect("index.jsp");
                break;
            case "view":
                response.sendRedirect("index.jsp");
                break;
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nombres = request.getParameter("nombres");
        String apellidos = request.getParameter("apellidos");
        int edad = Integer.parseInt(request.getParameter("edad"));
        
        Persona objper = new Persona();
        objper.setId(id);
        objper.setNombres(nombres);
        objper.setApellidos(apellidos);
        objper.setEdad(edad);
        
        HttpSession ses = request.getSession();
        ArrayList<Persona> lista = (ArrayList<Persona>)ses.getAttribute("listaper");
        
        int idt = objper.getId();
        if(idt == 0){
            int ultID;
            ultID = ultimoId(request);
            objper.setId(ultID);
            lista.add(objper);
        }else{
            lista.set(buscarIndice(request, id), objper);
        }
        ses.setAttribute("listaper", lista);
        response.sendRedirect("index.jsp");
        
    }
    
    private int ultimoId(HttpServletRequest request){
        HttpSession ses = request.getSession();
        ArrayList<Persona> lista = (ArrayList<Persona>) ses.getAttribute("listaper");
        
        int idaux = 0;
        
        for(Persona item : lista){
            idaux = item.getId();
        }
        return idaux+1;
    }
    
    private int buscarIndice(HttpServletRequest request, int id){
        HttpSession ses = request.getSession();
        ArrayList<Persona> lista = (ArrayList<Persona>) ses.getAttribute("listaper");
        int i=0;
        if(lista.size() > 0){
            while(i < lista.size()){
                if(lista.get(i).getId() == id){
                    break;
                }else{
                    i++;
                }
            }
        }
        return i;
    }
}
