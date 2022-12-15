import axios from "axios";
import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { API } from "../Config/ApiUrl";
import swal from "sweetalert";
import Menu from "../Menu/Menu";

const URL = API("listar_medicos");

const ListarMedicos = () => {
  const [Medico, setMedicos] = useState([]);
  const navigate = useNavigate();
  useEffect(() => {
    try {
      let tipoUsuario = "user";
      if (sessionStorage.getItem("key") === null) {
        swal("Acceso No Autorizado", "Debe digitar credenciales", "error");
        navigate("/");
      }
      JSON.parse(sessionStorage.getItem("roles")).forEach((element) => {
        if (element.nombreRol === "ADMIN") {
          tipoUsuario = "admin";
        }
        if (element.nombreRol === "PACIENTE") {
          tipoUsuario = "paciente";
        }
      });
      if (tipoUsuario === "paciente") {
        swal(
          "Acceso No Autorizado para paciente",
          "Debe digitar credenciales",
          "error"
        );
        navigate("/menu");
      } else {
        getMedicos();
      }
    } catch (error) {}
  }, []);

  const getMedicos = async () => {
    try {
      const traerMedicos = await axios({
        method: "GET",
        url: URL,
      });
      setMedicos(traerMedicos.data);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <Menu />
      <div className="container">
        <table className="table">
          <thead className="table-primary">
            <tr>
              <th>Nombre</th>
              <th>Apellido</th>
              <th>Documento</th>
              <th>Acciones</th>
            </tr>
          </thead>
          <tbody>
            {Medico.map((medico) => (
              <tr key={medico.idUsuario}>
                <td>{medico.nombreUsuario}</td>
                <td>{medico.apellidoUsuario}</td>
                <td>{medico.documentoUsuario}</td>
                <td>
                {" "}
                    <Link
                      className="btn btn-outline-info"
                      to={`/editar_usuario/${medico.idUsuario}`}
                    >
                      <i className="fa-solid fa-user-pen"></i>
                    </Link>{" "}
                    <Link
                      className="btn btn-outline-info"
                      to={`/agenda/${medico.idUsuario}`}
                    >
                      <i className="fa-solid fa-calendar-days"></i>
                    </Link>
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
    </>
  );
};

export default ListarMedicos;
