import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { API, APIC } from "../Config/ApiUrl";
import swal from "sweetalert";
import axios from "axios";

const URL = APIC("agenda_medico");
const URLC = APIC("crear_agenda");
const URLE = APIC("delete/")

const headers = {
  user: sessionStorage.getItem("user"),
  key: sessionStorage.getItem("key"),
};

const CrearAgenda = () => {
  const { id } = useParams();
  const [Cita, setCitas] = useState([]);
  const navigate = useNavigate();
  const [fechaInicio, setFechaInicio] = useState("");

  useEffect(() => {
    try {
      if (sessionStorage.getItem("key") === null) {
        swal("Acceso No Autorizado", "Debe digitar credenciales", "error");
        navigate("/");
      }
      getAgenda();
    } catch (error) {}
  }, []);

  const getAgenda = async () => {
    try {
      const agenda = await axios({
        method: "GET",
        url: URL + "/" + id,
        headers: headers,
      });
      setCitas(agenda.data);
    } catch (error) {
      console.log(error);
    }
  };
  const createAgenda = async () => {
    try {
    let fecha = fechaInicio.split('-');
    let year = fecha[0];
    let month = fecha[1];
    let day = fecha[2];
      const agenda = await axios({
        method: "POST",
        url: URLC + "/" + year + "/" + month + "/" + day,
        data:{
            estadoCita :false,
            medico:{
              idUsuario: id
            }
        },
        headers: headers,
      });
      swal("Cita agendada", agenda.data.message, "success");
      getAgenda();
    } catch (error) {
      console.log(error);
    }
  };

  const eliminarCita = async (id) => {
    swal({
      title: "Eliminar Registro",
      text: "Esta seguro de eliminar el registro",
      icon: "warning",
      buttons: true,
      dangerMode: true,
    }).then(async (willDelete) => {
      if (willDelete) {
        try {
          const eliminar = await axios({
            method: "DELETE",
            url: URLE + id,
          });

          swal("Registro elimindo", eliminar.data.message, "success");
          getAgenda();
        } catch (error) {
          swal("Acceso No Autorizado", JSON.parse(error.request), "error");
        }
      } else {
        swal("El registro no se borró");
      }
    });
  };

  return (
    <div>
      <div id="app" className="container col-12">
        <div style={{ marginTop: "60px" }}>
          <h3>Agenda</h3>
        </div>
        <table className="table">
          <thead className="table-primary">
            <tr>
              <th>Fecha</th>
              <th>Hora</th>
              <th>Paciente</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {Cita.map((cita) => (
              <tr key={cita.idCita}>
                <td>{cita.fechaCita}</td>
                <td>{cita.horaCita}</td>
                {cita.paciente == null ? (
                  <td>Sin agendar</td>
                ) : (
                  <td>
                    {cita.paciente.nombreUsuario}{" "}
                    {cita.paciente.apellidoUsuario}
                  </td>
                )}
                <td>
                <Link
                    className="btn btn-outline-danger"
                      onClick={() => eliminarCita(cita.idCita)}
                    >
                    <i className="fa-solid fa-trash"></i>
                    </Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        <div style={{ marginTop: "16px" }}>
          <Link
            className="btn btn-outline-primary"
            onClick={createAgenda}
            style={{ marginRight: "16px" }}
          >
            Crear Agenda
          </Link>
          <Link className="btn btn-outline-primary" to="/medicos">
            Regresar
          </Link>
        </div>
        <div className="input-group mb-2" style={{ marginTop: "16px" }}>
          <span className="input-group-text">Fecha de inicio: </span>
          <input
            type="date"
            value={fechaInicio}
            onChange={(e) => setFechaInicio(e.target.value)}
            className="form-control"
          />
        </div>
      </div>
    </div>
  );
};

export default CrearAgenda;
