package com.emergentes.dao;

import com.emergentes.conDB.ConexionDB;
import com.emergentes.modelos.Consulta;
import com.emergentes.modelos.DetalleConsulta;
import com.emergentes.modelos.Doctor;
import com.emergentes.modelos.Especialidad;
import com.emergentes.modelos.MedicalConsulta;
import com.emergentes.modelos.contarconsultas;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class consultaDAOimpl extends ConexionDB implements consultaDAO {

    @Override
    public void insert(Consulta cons) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "SELECT agregar_consulta(?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = getConnection();

            ps = conn.prepareStatement(sql);
            ps.setString(1, cons.getNombrePaciente());
            ps.setString(2, cons.getApellidoPaciente());
            ps.setDate(3, cons.getFechaConsulta());
            ps.setString(4, cons.getDiagnostico());
            ps.setString(5, cons.getTratamiento());
            ps.setString(6, cons.getCel());
            ps.setString(7, cons.getGptcon());
            //ps.setInt(8, cons.getIdoc());
            ps.setInt(8, cons.getIdoc());

            ps.execute();
        } catch (Exception e) {
            throw e;
        } finally {
            //Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public void update(Consulta detalle) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "SELECT actualizar_consulta(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            //int idEspecialidad = obtenerIdEspecialidadPorDoctor(detalle.getIdoc());

            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, detalle.getId());
            ps.setInt(2, detalle.getMedicalConsultaId());
            ps.setInt(3, detalle.getDetalleConsultaId());
            ps.setString(4, detalle.getNombrePaciente());
            ps.setString(5, detalle.getApellidoPaciente());
            ps.setDate(6, detalle.getFechaConsulta());
            ps.setString(7, detalle.getDiagnostico());
            ps.setString(8, detalle.getTratamiento());
            ps.setString(9, detalle.getCel());
            ps.setString(10, detalle.getGptcon());
            ps.setInt(11, detalle.getIdoc());
            ps.execute();
        } catch (Exception e) {
            throw e;
        } finally {
            //Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public void delete(int medicalConsultaId, int detalleConsultaId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            String sql = "SELECT eliminar_consulta(? , ?);";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, medicalConsultaId);
            ps.setInt(2, detalleConsultaId);
            ps.execute();
        } catch (Exception e) {
            throw e;
        } finally {
            //Connection conn = null;
            this.desconectar(conn);

        }
    }

    @Override
    public List<Consulta> getByDate(Date fecha) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Consulta> consultas = new ArrayList<>();

        try {
            conn = getConnection();
            String sql = "SELECT p.id, mc.id AS medicalConsultaId ,dc.id AS detalleConsultaId, p.firstname, p.lastname, mc.createdate, dc.diagnostic, dc.treatment, dc.celular, dc.analisis_medico "
                    + "FROM paciente p "
                    + "INNER JOIN medical_consulta mc ON p.id = mc.id_paciente "
                    + "INNER JOIN detalle_consulta dc ON mc.id = dc.id_medcon "
                    + "WHERE mc.createdate >= ? AND mc.createdate < ?";
            ps = conn.prepareStatement(sql);

            // Establecer el rango de fechas (desde las 00:00:00 hasta las 23:59:59)
            ps.setDate(1, fecha);
            ps.setDate(2, new java.sql.Date(fecha.getTime() + (24 * 60 * 60 * 1000))); // Sumar un día en milisegundos

            rs = ps.executeQuery();

            while (rs.next()) {
                Consulta detalle = new Consulta();
                detalle.setId(rs.getInt("id"));
                detalle.setMedicalConsultaId(rs.getInt("medicalConsultaId"));
                detalle.setDetalleConsultaId(rs.getInt("detalleConsultaId"));
                detalle.setNombrePaciente(rs.getString("firstname"));
                detalle.setApellidoPaciente(rs.getString("lastname"));
                detalle.setFechaConsulta(rs.getDate("createdate"));
                detalle.setDiagnostico(rs.getString("diagnostic"));
                detalle.setTratamiento(rs.getString("treatment"));
                detalle.setCel(rs.getString("celular"));
                detalle.setGptcon(rs.getString("analisis_medico"));

                consultas.add(detalle);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // Cerrar recursos
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return consultas;
    }

    @Override
    public List<Consulta> getAll() throws Exception {
        List<Consulta> detalles = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT p.id AS id,p.firstname,p.lastname, mc.createdate, dc.diagnostic, dc.treatment,dc.celular,dc.analisis_medico"
                    + ",mc.id AS medicalConsultaId,dc.id AS detalleConsultaId,mc.id_doctor as idoc"
                    + "FROM paciente p "
                    + "INNER JOIN medical_consulta mc ON p.id = mc.id_paciente "
                    + "INNER JOIN detalle_consulta dc ON mc.id = dc.id_medcon";

            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Consulta detalle = new Consulta(
                        rs.getInt("id"),
                        rs.getInt("medicalConsultaId"),
                        rs.getInt("detalleConsultaId"),
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getDate("createdate"),
                        rs.getString("diagnostic"),
                        rs.getString("treatment"),
                        rs.getString("celular"),
                        rs.getString("analisis_medico"),
                        rs.getInt("idoc")
                );

                detalles.add(detalle);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //Connection conn = null;
            this.desconectar(conn);

        }
        return detalles;

    }

    @Override
    public Consulta getBynomape(int id, int medicalConsultaId, int detalleConsultaId, String firstname, String lastname) throws Exception {
        Consulta detalles = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT p.id AS id , mc.id AS medicalConsultaId ,dc.id AS detalleConsultaId,p.firstname,p.lastname, mc.createdate, dc.diagnostic, dc.treatment,dc.celular,dc.analisis_medico "
                    + "FROM paciente p "
                    + "INNER JOIN medical_consulta mc ON p.id = mc.id_paciente "
                    + "INNER JOIN detalle_consulta dc ON mc.id = dc.id_medcon "
                    + "WHERE p.id = ? AND mc.id = ? AND dc.id = ? AND p.firstname = ? AND p.lastname = ?";

            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, medicalConsultaId);
            ps.setInt(3, detalleConsultaId);
            ps.setString(4, firstname);
            ps.setString(5, lastname);
            rs = ps.executeQuery();
            while (rs.next()) {
                detalles = new Consulta();
                detalles.setId(rs.getInt("id"));
                detalles.setMedicalConsultaId(rs.getInt("medicalConsultaId"));
                detalles.setDetalleConsultaId(rs.getInt("detalleConsultaId"));
                detalles.setNombrePaciente(rs.getString("firstname"));
                detalles.setApellidoPaciente(rs.getString("lastname"));
                detalles.setFechaConsulta(rs.getDate("createdate"));
                detalles.setDiagnostico(rs.getString("diagnostic"));
                detalles.setTratamiento(rs.getString("treatment"));
                detalles.setCel(rs.getString("celular"));
                detalles.setGptcon(rs.getString("analisis_medico"));

            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            desconectar(conn);
        }
        return detalles;
    }

    @Override
    public List<Object> getAll1() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Consulta> consultas = new ArrayList<>();
        List<String> especia = new ArrayList<>();
        try {
            conn = getConnection();
            String sql = "SELECT p.id AS id,p.firstname,p.lastname, mc.createdate, dc.diagnostic, dc.treatment,dc.celular,dc.analisis_medico"
                    + ",mc.id AS medicalConsultaId,dc.id AS detalleConsultaId,mc.id_doctor as idoc "
                    + "FROM paciente p "
                    + "INNER JOIN medical_consulta mc ON p.id = mc.id_paciente "
                    + "INNER JOIN detalle_consulta dc ON mc.id = dc.id_medcon";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setMedicalConsultaId(rs.getInt("medicalConsultaId"));
                consulta.setDetalleConsultaId(rs.getInt("detalleConsultaId"));
                consulta.setNombrePaciente(rs.getString("firstname"));
                consulta.setApellidoPaciente(rs.getString("lastname"));
                consulta.setFechaConsulta(rs.getDate("createdate"));
                consulta.setDiagnostico(rs.getString("diagnostic"));
                consulta.setTratamiento(rs.getString("treatment"));
                consulta.setCel(rs.getString("celular"));
                consulta.setGptcon(rs.getString("analisis_medico"));
                consulta.setIdoc(rs.getInt("idoc"));

                consultas.add(consulta);
            }

            // Obtener la lista de nombres de especialidades disponibles
            sql = "SELECT DISTINCT es.nombre "
                    + "FROM especialidad es "
                    + "INNER JOIN doctor doc ON es.id = doc.id_especialidad "
                    + "INNER JOIN medical_consulta mc ON doc.id = mc.id_doctor";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                especia.add(rs.getString("nombre"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //   Connection conn = null;
            this.desconectar(conn);

        }
// Devolver la lista de doctores y la lista de nombres de especialidades
        List<Object> result = new ArrayList<>();
        result.add(consultas);
        result.add(especia);
        return result;

    }

    // Método para obtener el ID de la especialidad por nombre
    public int obtenerIdEspecialidad(String nombreEspecialidad)
            throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idEpecialidad = -1; // Valor por defecto si no se encuentra la especialidad

        try {
            conn = getConnection();
            String sql = "SELECT id FROM especialidad WHERE nombre = ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombreEspecialidad);
            rs = ps.executeQuery();
            if (rs.next()) {
                idEpecialidad = rs.getInt("idoc");
            } else {
                throw new SQLException("La especialidad '" + nombreEspecialidad + "' no existe en la base de datos");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.desconectar(conn);
        }

        return idEpecialidad;
    }

    public ArrayList<String> obtenerEspecialidades() throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<String> especialidades = new ArrayList<>();

        try {
            conn = getConnection();
            String sql = "SELECT DISTINCT es.nombre "
                    + "FROM especialidad es "
                    + "INNER JOIN doctor doc ON es.id = doc.id_especialidad "
                    + "INNER JOIN medical_consulta mc ON doc.id = mc.id_doctor";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                especialidades.add(rs.getString("nombre"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.desconectar(conn);
        }

        return especialidades;
    }

    public int obtenerIdEspecialidadPorDoctor(int idDoctor) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idEspecialidad = -1; // Valor por defecto si no se encuentra la especialidad

        try {
            conn = getConnection();
            String sql = "select id from doctor where id_especialidad= ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idDoctor);
            rs = ps.executeQuery();
            if (rs.next()) {
                idDoctor = rs.getInt("id");
            } else {
                throw new SQLException("El doctor con ID '" + idDoctor + "' no existe en la base de datos");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.desconectar(conn);
        }

        return idEspecialidad;
    }

    public int obtenerIdDoctorPorEspecialidad(String nombreEspecialidad) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idDoctor = -1; // Valor por defecto si no se encuentra el doctor

        try {
            conn = getConnection();
            String sql = "SELECT doc.id FROM doctor doc "
                    + "INNER JOIN especialidad es ON doc.id_especialidad = es.id "
                    + "WHERE es.nombre = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, nombreEspecialidad);
            rs = ps.executeQuery();
            if (rs.next()) {
                idDoctor = rs.getInt("id");
            } else {
                throw new SQLException("No se encontró ningún doctor asociado a la especialidad '" + nombreEspecialidad + "'");
            }
        } finally {
            // Cerrar conexiones
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return idDoctor;
    }

    @Override
    public List<contarconsultas> getReporteConsultas() throws Exception {
        List<contarconsultas> lista = new ArrayList<>();
     
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConexionDB.getConnection();
            String sql = "SELECT e.nombre AS especialidad, mc.createdate AS fecha_consulta, COUNT(mc.id) AS total_consultas "
                    + "FROM public.medical_consulta mc "
                    + "JOIN public.doctor d ON mc.id_doctor = d.id "
                    + "JOIN public.especialidad e ON d.id_especialidad = e.id "
                    + "GROUP BY e.nombre, mc.createdate "
                    + "ORDER BY e.nombre, mc.createdate";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                contarconsultas reporte = new contarconsultas();
                reporte.setEspecie(rs.getString("especialidad"));
                reporte.setFecha(rs.getDate("fecha_consulta"));
                reporte.setTotal(rs.getInt("total_consultas"));
                lista.add(reporte);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // Cerrar conexiones
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        return lista;
        }
    
    }

}
