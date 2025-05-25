import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Index from "./pages/Index";
import Settings from "./pages/Settings";
import NotFound from "./pages/NotFound";
import LoginPage from "./pages/LoginPage";
import RecuperarSenha from "./pages/Recuperar-Senha";
import SolicitarAcesso from "./pages/Solicitar-Acesso";

const queryClient = new QueryClient();

const App = () => (
  <QueryClientProvider client={queryClient}>
    <TooltipProvider>
      <Toaster />
      <Sonner />
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Navigate to="/login" replace />} /> {/* Redirecionamento automático */}
          <Route path="/home" element={<Index />} /> {/* Página real de dashboard */}
          <Route path="/login" element={<LoginPage />} />
          <Route path="/recuperarsenha" element={<RecuperarSenha />} />
          <Route path="/solicitaracesso" element={<SolicitarAcesso />} />
          <Route path="/settings" element={<Settings />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </BrowserRouter>
    </TooltipProvider>
  </QueryClientProvider>
);

export default App;
