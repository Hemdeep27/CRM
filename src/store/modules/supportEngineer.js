import axios from "axios";
export default {
  state: {
    supportEngineer: [],
    supportList: [],
    supportEngineerDetails: [],
  },
  getters: {
    getSupportEnginner: (state) => state.supportEngineer,
    getSupportList: (state) => state.supportList,
    getSupportEngineerDetails: (state) => state.supportEngineerDetails,
  },
  mutations: {
    setSupportEngineer: (state, value) => (state.supportEngineer = value),
    setSupportList: (state, value) => (state.supportList = value),
    setSupportEngineerDetails: (state, value) =>
      (state.supportEngineerDetails = value),
  },
  actions: {
    async ADD_SUPPORT({ commit }, { payload, success }) {
      const response = await axios.post("/api/admin/addUser", payload);
      console.log(payload);
      console.log("from store", response);
      success(response.data);
      commit("setSupportEngineer", response.data);
    },
    async SUPPORT_LIST({ commit }) {
      const response = await axios.get("/api/admin/supportList");
      console.log(response.data);
      commit("setSupportList", response.data);
    },
    async SUPPORT_DETAILS({ commit }, { sid }) {
      const res = await axios.get(`/api/support/getSupportBySupportId/${sid}`);
      commit("setSupportEngineerDetails", res.data);
    },
  },
};
