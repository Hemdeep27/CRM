import axios from "axios";
export default {
  state: {
    ticketList: [],
    ticketAssign: [],
  },
  getters: {
    getTicketList: (state) => state.ticketList,
    getTicketAssign: (state) => state.ticketAssign,
  },
  mutations: {
    setTicketList: (state, value) => (state.ticketList = value),
    setTicketAssign: (state, value) => (state.ticketAssign = value),
  },
  actions: {
    async TICKET_ASSIGN({ commit }, { tId, sId }) {
      const response = await axios.post(
        `/api/admin/assignTicket/${tId}/${sId}`
      );
      console.log(response.data);
      commit("setTicketAssign", response.data);
    },
    async TICKET_LIST({ commit }, { status, success }) {
      const response = await axios.get(
        "/api/admin/getAllByTicketStatus/" + status
      );
      commit("setTicketList", response.data);
      success(response.data);
    },
  },
};
