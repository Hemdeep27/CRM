<template>
  <div>
    <!-- <nav-bar-1></nav-bar-1> -->
    <div class="container">
      <h1>Assigned Leads</h1>
      <!-- <p>{{ list }}</p> -->
      <div class="inner-container">
        <div v-for="(lead, index) in list" :key="index" class="assignLeads">
          <br />
          <p><b>leadId: </b>{{ lead.leadId }}</p>
          <p><b>leadInfo: </b>{{ lead.leadInfo }}</p>
          <p><b>userId: </b>{{ lead.userId }}</p>
          <p><b>action: </b>{{ lead.action }}</p>
          <button
            type="button"
            class="btn btn-outline-primary"
            @click="EditLead(lead.leadId, lead.leadInfo)"
          >
            Edit
          </button>
          <br /><br />
        </div>
      </div>
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
      list: [],
      leadId: "",
      leadInfo: "",
    };
  },

  async created() {
    await axios
      .get("/api/support/getleadsBySupportId/" + localStorage.getItem("id"))
      .then((res) => {
        this.list = res.data;
      });
    console.log(this.list);
  },

  methods: {
    async EditLead(leadId, leadInfo) {
      localStorage.setItem("leadId", leadId);
      localStorage.setItem("leadInfo", leadInfo);
      this.$router.push("/editLead");
    },
  },
};
</script>

<style scoped>
.assignLeads {
  /* border: 1px solid black; */
  width: 400px;
  /* height: 250px; */
  margin: 20px;
  box-shadow: 0 3px 20px rgba(0, 0, 0, 0.239);
  border-radius: 9px;
  padding-left: 20px;
  padding-right: 20px;
  text-align: left;
}

.inner-container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  word-wrap: break-word;
}
</style>
