import { useEffect, useState } from "react";
import { Link, useNavigate,useParams } from "react-router-dom";
import swal from "sweetalert";
import { API } from "../Config/ApiUrl";
import axios from "axios";

const URL = API("editar_usuario");
const URLF = API("buscar_usuario/");

const EditarUsuarios = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [nombreUsuario, setNombreUsuario] = useState("");
    const [apellidoUsuario, setApellidoUsuario] = useState("");
    const [documentoUsuario, setDocumentoUsuario] = useState("");
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");

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
          buscarUsuario();
        } catch (error) {}
      }, []);

    const guardar = async () => {
        try {
          const insertarCliente = await axios({
            method: "PUT",
            url: URL,
            data: {
                idUsuario: id,
                nombreUsuario: nombreUsuario,
                apellidoUsuario: apellidoUsuario,
                documentoUsuario: documentoUsuario,
                userName: userName,
                password: password,
            },
          });
          swal("Usuario actualizado", insertarCliente.data.message, "success").then(
            (value) => {
              navigate("/pacientes")
            }
          );
        } catch (error) {
            console.log(error);
            swal("Error",
            JSON.parse(error.request.response).message,
            "error");
        }
    };
    const buscarUsuario = async () => {
        const usuario = await axios({
          method: "GET",
          url: URLF + id,
          headers:headers,
        });
        setNombreUsuario(usuario.data.nombreUsuario);
        setApellidoUsuario(usuario.data.apellidoUsuario);
        setDocumentoUsuario(usuario.data.documentoUsuario);
        setUserName(usuario.data.userName);
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
            <Link className="btn btn-outline-primary" to="/pacientes">Regresar</Link>
          </form>
        </div>
      </>
    );
}
 
export default EditarUsuarios;