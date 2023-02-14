<template>
  <div>
    <!-- <nav-bar-1></nav-bar-1> -->
    <div class="box1">
      <label>leadInfo</label>
      <input v-model="leadInfo" type="text" name="leadInfo" />
      <!-- <p>you Typed: {{ leadInfo }}</p> -->
      <button type="button" class="btn btn-outline-primary" @click="submit">
        Save
      </button>
    </div>
  </div>
</template>
<script>
// import NavBar1 from "@/components/NavBar1.vue";
import axios from "axios";
export default {
  components: {
    // NavBar1,
  },
  data() {
    return {
      list: undefined,
      leadId: "",
      leadInfo: "",
    };
  },
  async created() {
    const response = await axios.get(
      "/api/support/getLeadByLeadId/" + localStorage.getItem("leadId")
    );
    console.log(response);
    this.leadInfo = response.data.leadInfo;
  },

  methods: {
    async submit() {
      const res = await axios.post("/api/support/editLead/", {
        leadId: localStorage.getItem("leadId"),
        leadInfo: this.leadInfo,
      });
      console.log(res);
      this.$toasted.success("Lead Info Added", {
        position: "top-center",
        duration: 2000,
      });
    },
  },
};
</script>
<style scoped>
.box1 {
  border-radius: 9px;
  width: 400px;
  height: 200px;
  margin-left: 500px;
  margin-top: 120px;
  padding-top: 20px;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
}
</style>
