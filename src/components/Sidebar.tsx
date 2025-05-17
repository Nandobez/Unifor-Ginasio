import React, { useState } from "react";
import { Link, useLocation } from "react-router-dom";
import { Button } from "./ui/button";
import { CalendarIcon, Settings, MapPin } from "lucide-react";
import { cn } from "@/lib/utils";
import { FacilityMap } from "./FacilityMap";
import { useScheduler } from "@/contexts/SchedulerContext";
import { DayOfWeek } from "@/types/scheduler";
import { TooltipProvider } from "./ui/tooltip";
import { dayTranslations } from "@/utils/translations";

const Sidebar: React.FC = () => {
  const location = useLocation();
  const { filteredReservations } = useScheduler();
  const [showMap, setShowMap] = useState(true);
  const [isMapExpanded, setIsMapExpanded] = useState(false);

  // Get the day of the week for today to use as default
  const getCurrentDay = (): DayOfWeek => {
    const days: DayOfWeek[] = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
    const today = new Date().getDay(); // 0 is Sunday, 1 is Monday, etc.
    return days[today];
  };

  const [selectedDay, setSelectedDay] = useState<DayOfWeek | undefined>(getCurrentDay());

  const isActive = (path: string) => {
    return location.pathname === path;
  };

  // Toggle between showing all days or specific day
  const toggleDayFilter = () => {
    setSelectedDay(selectedDay ? undefined : getCurrentDay());
  };

  // Handle map expansion toggle
  const toggleMapExpand = () => {
    setIsMapExpanded(!isMapExpanded);
  };

  return (
    <div className="h-full bg-scheduler-blue-dark text-white w-56 flex flex-col shadow-lg">
      <div className="p-5 flex flex-col items-start">
        <div className="w-full -ml-5">
          <img
            src="/logos/4803e70c-58dc-43a5-9986-aebab55e8586.png"
            alt="Unifor Logo"
            className="w-32 h-auto mb-4"
          />
        </div>
        <h1 className="text-xl font-bold">Ginásio Unifor</h1>
        <p className="text-sm text-scheduler-gray-light mt-1">Painel Administrativo</p>
      </div>

      <nav className="mt-6 flex flex-col gap-2 px-3">
        <Link to="/">
          <Button
            variant="ghost"
            className={cn(
              "w-full justify-start text-white hover:text-white hover:bg-scheduler-blue-medium rounded-lg",
              isActive("/") && "bg-scheduler-blue-medium"
            )}
          >
            <CalendarIcon className="mr-2 h-4 w-4" />
            Agenda
          </Button>
        </Link>
        <Link to="/settings">
          <Button
            variant="ghost"
            className={cn(
              "w-full justify-start text-white hover:text-white hover:bg-scheduler-blue-medium rounded-lg",
              isActive("/settings") && "bg-scheduler-blue-medium"
            )}
          >
            <Settings className="mr-2 h-4 w-4" />
            Configurações
          </Button>
        </Link>
        <Button
          variant="ghost"
          onClick={() => setShowMap(!showMap)}
          className="w-full justify-start text-white hover:text-white hover:bg-scheduler-blue-medium rounded-lg mt-2"
        >
          <MapPin className="mr-2 h-4 w-4" />
          {showMap ? "Esconder Mapa" : "Mostrar Mapa"}
        </Button>
      </nav>

      {showMap && (
        <TooltipProvider>
          <div className="mt-4 px-3 pb-3 flex-grow">
            <div className="flex justify-between items-center mb-2">
              <span className="text-sm font-medium">Mapa de Locais</span>
              <button
                onClick={toggleDayFilter}
                className="text-[10px] bg-scheduler-blue-medium px-2 py-0.5 rounded hover:bg-scheduler-blue-dark transition-colors"
              >
                {selectedDay ? dayTranslations[selectedDay] : "Todos os dias"}
              </button>
            </div>
            <div className="bg-white rounded-lg overflow-hidden shadow-inner h-[180px]">
              <FacilityMap
                reservations={filteredReservations}
                selectedDay={selectedDay}
                isExpanded={isMapExpanded}
                onToggleExpand={toggleMapExpand}
              />
            </div>
          </div>
        </TooltipProvider>
      )}

      <div className="mt-auto p-4 text-xs text-scheduler-gray-light">
        <p>© 2025 Ginásio Unifor</p>
        <p className="mt-1">Acesso Administrativo</p>
      </div>
    </div>
  );
};

export default Sidebar;
