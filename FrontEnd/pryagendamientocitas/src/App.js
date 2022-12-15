import './App.css';
// import './styles/global.css'
import {Route,Routes,HashRouter} from "react-router-dom"
import Login from './components/Login/Login';
import Menu from './components/Menu/Menu';
import ListarPacientes from './components/Paciente/ListarPacientes';
import HabilitarUsuarios from './components/Admin/HabilitarUsuarios';
import RegistroUsuarios from './components/Usuarios/RegistroUsuarios';
import AgendarCitas from './components/Paciente/AgendarCitas';
import CancelarCita from './components/Paciente/CancelarCita';
import ConsultarCita from './components/Paciente/ConsultarCita';
import ListarMedicos from './components/Admin/ListarMedicos';
import CrearAgenda from './components/Admin/CrearAgenda';
import EditarUsuarios from './components/Admin/EditarUsuarios';

function App() {
  return (
    <>
      <HashRouter>
        <Routes>
          <Route exact path="/" element={<Login/>}/>
          <Route exact path="/menu" element={<Menu/>}/>
          <Route exact path="/pacientes" element={<ListarPacientes/>}/>
          <Route exact path="/habilitar_usuario" element={<HabilitarUsuarios/>}/>
          <Route exact path="/registro" element={<RegistroUsuarios/>}/>
          <Route exact path="/agendar" element={<AgendarCitas/>}/> 
          <Route exact path="/cancelar" element={<CancelarCita/>}/>
          <Route exact path="/consultar" element={<ConsultarCita/>}/>
          <Route exact path="/medicos" element={<ListarMedicos/>}/>
          <Route exact path="/agenda/:id" element={<CrearAgenda/>}/>  
          <Route exact path="/editar_usuario/:id" element={<EditarUsuarios/>}/>  
        </Routes>
      </HashRouter>
    </>
  );
}

export default App;
