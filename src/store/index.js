import Vue from "vue";
import Vuex from "vuex";
import supportEngineer from "./modules/supportEngineer";
import leadsList from "./modules/leadsList";
import ticketCalls from "./modules/ticketCalls";
import login from "./modules/login";

Vue.use(Vuex);

export default new Vuex.Store({
  modules: { supportEngineer, leadsList, ticketCalls, login },
});
