import axios from "axios";

export default {
  state: {
    unAssignedLeads: [],
    tickets: [],
    leadAssign: [],
    assignedLeadList: [],
  },
  getters: {
    getUnAssignedLeads: (state) => state.unAssignedLeads,
    getTickets: (state) => state.tickets,
    getLeadAssign: (state) => state.leadAssign,
    getAssignedLeadList: (state) => state.assignedLeadList,
  },
  mutations: {
    setUnAssignedLeads: (state, value) => (state.unAssignedLeads = value),
    setTickets: (state, value) => (state.tickets = value),
    setLeadAssign: (state, value) => (state.leadAssign = value),
    setAssignedLeadList: (state, value) => (state.assignedLeadList = value),
  },
  actions: {
    async ASSIGNED_LEADS_LIST({ commit }) {
      const response = await axios.get("/api/admin/assignedLeadList");
      commit("setAssignedLeadList", response.data);
    },
    async ASSIGN_LEAD({ commit }, { lId, sId }) {
      const response = await axios.post(`/api/admin/assignLead/${lId}/${sId}`);
      console.log(response.data);
      commit("setLeadAssign", response.data);
    },
    async TICKETS_LIST({ commit }, { details }) {
      const response = await axios
        .post("/api/admin/createTicket", details)
        .then((response) => {
          console.log(response.data);
          commit("setTickets", response.data);
        });
      console.log(response.data);
    },
    async ALL_LEADS({ commit }, success) {
      const res = await axios.get("/api/admin/unassignedLeadList");
      console.log("unassigned", res.data);
      commit("setUnAssignedLeads", res.data);
      success(res.data);
    },
  },
};
