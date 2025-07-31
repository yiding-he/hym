import {FormConfig} from "../form/FormConfig";
import {Header} from "../table/DataTableCore";
import {ApiType} from "../../common/ApiClient";

export class CrudConfig {
  title: string = "";
  form: FormConfig = new FormConfig();
  table: TableConfig = new TableConfig();
  api?: ApiType<any, any>;
  operations: OperationsConfig = new OperationsConfig();

  constructor(config?: Partial<CrudConfig>) {
    if (config) {
      Object.assign(this, config);
    }
  }
}

export class TableConfig {
  headers: Header[] = [];

  constructor(config?: Partial<TableConfig>) {
    if (config) {
      Object.assign(this, config);
    }
  }
}

export type Operation = {
  label: string;
  func?: () => void;
  forRow?: boolean;
}

export class OperationsConfig {
  operations: Operation[] = [];

  constructor(config?: Partial<OperationsConfig>) {
    if (config) {
      Object.assign(this, config);
    }
  }
}