import axios from "axios";
import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { API } from "../Config/ApiUrl";
import swal from "sweetalert";
import Menu from "../Menu/Menu";

const URL = API("listar_pacientes");

const ListarPacientes = () => {
  const [Paciente, setPacientes] = useState([]);
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
        getPacientes();
      }
    } catch (error) {}
  }, []);

  const getPacientes = async () => {
    try {
      const login = await axios({
        method: "GET",
        url: URL
      });
      setPacientes(login.data);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <Menu />
      <div className="container">
        <Link className="btn btn-outline-primary" to={`/habilitar_usuario`}>
          <i className="fa-solid fa-user-plus"></i>
        </Link>
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
            {Paciente.map((paciente) => (
              <tr key={paciente.idUsuario}>
                <td>{paciente.nombreUsuario}</td>
                <td>{paciente.apellidoUsuario}</td>
                <td>{paciente.documentoUsuario}</td>
                <td>
                  <Link
                    className="btn btn-outline-info" 
                    to={`/editar_usuario/${paciente.idUsuario}`}
                  >
                    <i className="fa-solid fa-user-pen"></i>
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

export default ListarPacientes;
