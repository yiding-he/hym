import {FormConfig} from "../form/FormConfig";

export class FormDialogConfig {
  title: string = "";
  form: FormConfig = new FormConfig();
  onSubmit?: (formData: any) => void;

  constructor(config?: Partial<FormDialogConfig>) {
    if (config) {
      Object.assign(this, config);
    }
  }
}
