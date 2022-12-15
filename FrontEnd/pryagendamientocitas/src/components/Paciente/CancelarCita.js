import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import swal from "sweetalert";

import { APIC } from "../Config/ApiUrl";
import axios from "axios";

const URL = APIC("citas_paciente");
const URLC = APIC("estado_cita");

const CancelarCita = () => {
    const [Cita, setCitas] = useState([]);
    const navigate = useNavigate();
  
    const headers = {
      user: sessionStorage.getItem("user"),
      key: sessionStorage.getItem("key"),
    };
    useEffect(() => {
      try {
        if (sessionStorage.getItem("key") === null) {
          swal("Acceso No Autorizado", "Debe digitar credenciales", "error");
          navigate("/");
        }
        getCitas(sessionStorage.getItem("id"))
      } catch (error) {}
    }, []);
    const getCitas = async (id) => {
        console.log(URL + "/" + id);
        try {
        const citasPaciente = await axios({
          method: "GET",
          url: URL + "/" + id,
          headers: headers,
        });
        setCitas(citasPaciente.data);
      } catch (error) {
        console.log(error);
      }
    };
    const cancelarCita = async (id) => {
      try {
        console.log(id);
        const cancelar = await axios({
          method: "PUT",
          url: URLC,
          data: {
            idCita: id,
            paciente: null,
          },
          headers: headers,
        });
        swal("Cita cancelada", cancelar.data.message, "success");
        getCitas(sessionStorage.getItem("id"))
      } catch (error) {
          console.log(error);
        swal("Error", JSON.parse(error.request.response).message, "error");
      }
    };
    
    return (  
        <div>
        <div id="app" className="container col-12">
          <div style={{ marginTop: "60px" }}>
            <h3>Cancelar Citas</h3>
          </div>
          <table className="table">
            <thead className="table-primary">
              <tr>
                <th>Fecha</th>
                <th>Hora</th>
                <th>Medico</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {Cita.map((cita) => (
                <tr key={cita.idCita}>
                  <td>{cita.horaCita}</td>
                  <td>{cita.fechaCita}</td>
                  <td>
                    {cita.medico.nombreUsuario} {cita.medico.apellidoUsuario}
                  </td>
                  <td>
                    <Link className="btn btn-outline-danger" onClick={() => cancelarCita(cita.idCita)}>
                    <i className="fa-solid fa-xmark"></i>
                    </Link>{" "}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          <Link
            className="btn btn-outline-primary"
            to="/menu"
            style={{ marginTop: "16px" }}
          >
            Regresar
          </Link>
        </div>
      </div>
    );
}
 
export default CancelarCita;