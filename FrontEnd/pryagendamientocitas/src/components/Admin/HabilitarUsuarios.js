import Menu from "../Menu/Menu";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import swal from "sweetalert";
import { API } from "../Config/ApiUrl";
import axios from "axios";

const URL = API("create");

const HabilitarUsuarios = () => {
  const navigate = useNavigate();
  const [documentoUsuario, setDocumentoUsuario] = useState("");
  const [regresar, setRegresar] = useState(0);

  const headers = {
    user: sessionStorage.getItem("user"),
    key: sessionStorage.getItem("key"),
  };

  useEffect(() => {
    if (sessionStorage.getItem("key") === null) {
      swal("Acceso No Autorizado", "Debe digitar credenciales", "error");
      navigate("/");
    }
  }, []);

  useEffect(() => {
    if (regresar !== 0) navigate("/pacientes");
  }, [regresar]);

  const guardar = async () => {
    let roles = document.getElementsByClassName("role");
    let listaRoles = [];
    for (const rol of roles) {
      if (rol.checked) {
        listaRoles.push(rol.value);
      }
    }
    console.log(listaRoles);
    try {
      const habilitar = await axios({
        method: "POST",
        url: URL,
        data: {
          documentoUsuario: documentoUsuario,
          roles: listaRoles
        },
        headers: headers,
      });
      swal("Registro creado", habilitar.data.message, "success").then(
        (value) => {
          setRegresar(1);
        }
      );
    } catch (error) {
        swal("Error",
        JSON.parse(error.request.response).message,
        "error");
    }
  };

  return (
    <>
      <Menu></Menu>
      <div className="container col-5">
        <form onSubmit={guardar}>
          <div className="mb-3">
            <label className="form-label">Documento del Paciente </label>{" "}
            <input
              className="form-control"
              type="text"
              value={documentoUsuario}
              onChange={(e) => setDocumentoUsuario(e.target.value)}
            ></input>
          </div>
          <div className="mb-3">
            <input
              className="form-check-input role"
              type="checkbox"
              value="Admin"
            />{" "}
            <label className="form-check-label">Admin</label>
            {"   "}
            <input
              className="form-check-input role"
              type="checkbox"
              value="Medico"
            />{" "}
            <label className="form-check-label">Medico</label>
            {"   "}
            <input
              className="form-check-input role"
              type="checkbox"
              value="Paciente"
            />{" "}
            <label className="form-check-label">Paciente</label>
          </div>
          <button type="submit" className="btn btn-outline-primary">
            Guardar
          </button>{" "}
          <Link className="btn btn-outline-primary" to="/pacientes">
            Regresar
          </Link>
        </form>
      </div>
    </>
  );
};

export default HabilitarUsuarios;
