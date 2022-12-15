import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import swal from "sweetalert";

import { APIC } from "../Config/ApiUrl";
import axios from "axios";

const URL = APIC("citas_paciente");

const ConsultarCita = () => {
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

    return (  
        <div>
        <div id="app" className="container col-12">
          <div style={{ marginTop: "60px" }}>
            <h3>Citas</h3>
          </div>
          <table className="table">
            <thead className="table-primary">
              <tr>
                <th>Fecha</th>
                <th>Hora</th>
                <th>Medico</th>
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
 
export default ConsultarCita;