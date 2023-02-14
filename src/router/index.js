import Vue from "vue";
import VueRouter from "vue-router";
// import HomeView from "../views/HomeView.vue";
import HomePageAdmin from "../views/HomePageAdmin.vue";
import CreateSupport from "../views/CreateSupport.vue";
import TicketView from "../views/TicketView.vue";
// import LoginView from "../views/LoginView.vue";
import LeadView from "../views/LeadView.vue";
import SupportLogin from "../views/SupportLogin.vue";
import SupportEngineer from "../views/SupportEngineer.vue";
import EditLead from "../views/EditLead.vue";
import AssignedLead from "../views/AssignedLead.vue";
import EditTicket from "../views/EditTicket.vue";
import SupportTicket from "../views/SupportTicket.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "supportlogin",
    component: SupportLogin,
  },
  {
    path: "/editticket",
    name: "editticket",
    component: EditTicket,
  },
  {
    path: "/supportticket",
    name: "supportticket",
    component: SupportTicket,
  },
  {
    path: "/editlead",
    name: "editlead",
    component: EditLead,
  },
  {
    path: "/assignedlead",
    name: "assignedlead",
    component: AssignedLead,
  },
  {
    path: "/leads",
    name: "leads",
    component: LeadView,
  },
  {
    path: "/supportengineer",
    name: "supportengineer",
    component: SupportEngineer,
  },
  {
    path: "/tickets",
    name: "tickets",
    component: TicketView,
  },
  {
    path: "/adminhome",
    name: "adminhome",
    component: HomePageAdmin,
  },
  {
    path: "/createsupport",
    name: "createsupport",
    component: CreateSupport,
  },
  {
    path: "/about",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
