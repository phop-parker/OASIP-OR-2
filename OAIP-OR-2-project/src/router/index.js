import { createRouter, createWebHistory } from "vue-router";
import EventBooking from '../views/EventBooking.vue';
import ViewsAllAppointment from '../views/ViewsAllAppointment.vue';

const history = createWebHistory();
const routes = [
  {
    path: "/",
    name: "EventBooking",
    component: EventBooking,
  },
  {
    path: "/viewsall",
    name: "ViewsAllAppointment",
    component: ViewsAllAppointment,
  },
];
const router = createRouter({ history, routes });
export default router;
