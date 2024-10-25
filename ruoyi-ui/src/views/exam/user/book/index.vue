<template>

  <data-table
    ref="pagingTable"
    :options="options"
    :list-query="listQuery"
  >
    <template #filter-content>

      <el-input v-model="listQuery.params.title" placeholder="搜索题目内容" style="width: 200px;" class="filter-item"/>

      <el-button class="filter-item" style="float: right" type="primary" icon="el-icon-magic-stick" @click="startTrain">
        错题训练
      </el-button>

    </template>

    <template #data-columns>

      <el-table-column
        label="题目内容"
        show-overflow-tooltip
      >
        <template v-slot="scope">
          <router-link :to="{ name: 'ViewQu', params:{ id: scope.row.quId}}">
            {{ scope.row.title }}
          </router-link>
        </template>
      </el-table-column>

      <el-table-column
        label="错误次数"
        prop="wrongCount"
        align="center"
        width="100px"
      />

      <el-table-column
        label="更新时间"
        align="center"
        prop="updateTime"
        width="180px"
      />

    </template>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

  </data-table>

</template>

<script>
//import DataTable from '@/components/DataTable'

import {listRepo, listWrong} from "@/api/exam/book";

export default {
  name: 'QuList',
  //components: { DataTable },
  data() {
    return {
      // 总条数
      total: 0,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        params: {
          title: '',
          examId: ''
        }
      },

      options: {

        // 可批量操作
        multi: true,

        // 批量操作列表
        multiActions: [
          {
            value: 'delete',
            label: '删除'
          }
        ],
        // 列表请求URL
        //listUrl: '/exam/api/user/wrong-book/paging',
        // 删除请求URL
        //deleteUrl: '/exam/api/user/wrong-book/delete'
      }
    }
  },
  created() {
    /*const id = this.$route.params.examId
    if (typeof id !== 'undefined') {
      this.listQuery.params.examId = id
      this.fetchData(id)
    }*/

    this.getList();
  },
  methods: {
    /** 查询题库信息列表 */
    getList() {
      this.loading = true;
      listWrong({pageNum: this.queryParams.pageNum, pageSize: this.queryParams.pageSize}, this.queryParams).then(response => {
        this.repoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    startTrain() {
      this.$router.push({name: 'BookTraining', params: {examId: this.listQuery.params.examId}})
    }

  }
}
</script>
