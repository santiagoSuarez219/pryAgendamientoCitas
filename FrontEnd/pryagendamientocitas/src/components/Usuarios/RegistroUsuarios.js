import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import swal from "sweetalert";
import { API } from "../Config/ApiUrl";
import axios from "axios";

const URL = API("registro_usuario");

const RegistroUsuarios = () => {
    const navigate = useNavigate();
    const [nombreUsuario, setNombreUsuario] = useState("");
    const [apellidoUsuario, setApellidoUsuario] = useState("");
    const [documentoUsuario, setDocumentoUsuario] = useState("");
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");

    const guardar = async () => {
        try {
          const insertarCliente = await axios({
            method: "PUT",
            url: URL,
            data: {
                nombreUsuario: nombreUsuario,
                apellidoUsuario: apellidoUsuario,
                documentoUsuario: documentoUsuario,
                userName: userName,
                password: password,
            },
          });
          swal("Usuario creado", insertarCliente.data.message, "success").then(
            (value) => {
              navigate("/")
            }
          );
        } catch (error) {
            console.log(error);
            swal("Error",
            JSON.parse(error.request.response).message,
            "error");
        }
      };

    return (  
        <>
        <div className="container col-5" style={{padding: "16px"}}>
          <form onSubmit={guardar}>
            <div className="mb-3">
              <label className="form-label">Nombre</label>{" "}
              <input
                className="form-control"
                type="text"
                value={nombreUsuario}
                onChange={(e) => setNombreUsuario(e.target.value)}
              ></input>
            </div>
            <div className="mb-3">
              <label className="form-label">Apellido</label>{" "}
              <input
                className="form-control"
                type="text"
                value={apellidoUsuario}
                onChange={(e) => setApellidoUsuario(e.target.value)}
              ></input>
            </div>
            <div className="mb-3">
              <label className="form-label">Documento</label>{" "}
              <input
                className="form-control"
                type="text"
                value={documentoUsuario}
                onChange={(e) => setDocumentoUsuario(e.target.value)}
              ></input>
            </div>
            <div className="mb-3">
              <label className="form-label">Username</label>{" "}
              <input
                className="form-control"
                type="text"
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
              ></input>
            </div>
            <div className="mb-3">
              <label className="form-label">Contraseña</label>{" "}
              <input
                className="form-control"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              ></input>
            </div>
            <button type="submit" className="btn btn-outline-primary">
              Guardar
            </button>{" "}
            <Link className="btn btn-outline-primary" to="/">Regresar</Link>
          </form>
        </div>
      </>
    );
}
 
export default RegistroUsuarios;