import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import swal from "sweetalert";

import { APIC } from "../Config/ApiUrl";
import axios from "axios";

const URL = APIC("citas_disponibles");
const URLA = APIC("estado_cita");

const AgendarCitas = () => {
  const [Cita, setCitas] = useState([]);
  const navigate = useNavigate();
  const [fechaCita, setFechaCita] = useState("");

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
    } catch (error) {}
  }, []);

  const getCitas = async () => {
    try {
      console.log(fechaCita);
      const citasDisponibles = await axios({
        method: "GET",
        url: URL + "/" + fechaCita,
        headers: headers,
      });
      setCitas(citasDisponibles.data);
    } catch (error) {
      console.log(error);
    }
  };
  const agendarCita = async (id) => {
    try {
        console.log(id);
      const agendar = await axios({
        method: "PUT",
        url: URLA,
        data: {
          idCita: id,
          paciente: {
            idUsuario: sessionStorage.getItem("id"),
          },
        },
        headers: headers,
      });
      swal("Agenda creada", agendar.data.message, "success");
      getCitas();
    } catch (error) {
        console.log(error);
      swal("Error", JSON.parse(error.request.response).message, "error");
    }
  };

  return (
    <div>
      <div id="app" className="container col-12">
        <div style={{ marginTop: "60px" }}>
          <h3>Agendar Citas</h3>
        </div>
        <div className="input-group mb-2" style={{ marginTop: "16px" }}>
          <span className="input-group-text">Fecha: </span>
          <input
            type="date"
            value={fechaCita}
            onChange={(e) => setFechaCita(e.target.value)}
            className="form-control"
          />
          <button
            type="button"
            onClick={getCitas}
            className="btn btn-outline-primary"
          >
            Buscar Citas
          </button>
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
                <td>{cita.fechaCita}</td>
                <td>{cita.horaCita}</td>
                <td>
                  {cita.medico.nombreUsuario} {cita.medico.apellidoUsuario}
                </td>
                <td>
                  <Link className="btn btn-outline-success" onClick={() => agendarCita(cita.idCita)}>
                    <i className="fa-solid fa-check"></i>
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
};

export default AgendarCitas;
