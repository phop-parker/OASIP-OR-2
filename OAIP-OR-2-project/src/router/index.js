import { createRouter, createWebHistory } from "vue-router";
import EventBooking from '../views/EventBooking.vue';
import ViewsAllAppointment from '../views/ViewsAllAppointment.vue';
import CategoryList from '../views/CategoryList.vue';
import Home from '../views/Home.vue';

const history = createWebHistory('/or2/');
const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/categorylist",
    name: "CategoryList",
    component: CategoryList,
  },
  {
    path: "/eventbooking",
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
