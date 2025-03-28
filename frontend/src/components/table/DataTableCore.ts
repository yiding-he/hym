export type Header = {
  label: string,
  name?: string,
  children?: Header[]
}

export type HeaderCell = {
  label: string;
  name?: string;
  rowIndex?: number;
  colIndex: number;
  rowSpan: number;
  colSpan: number;
};

export type FinalColumn = {
  label: string,
  name: string
}

/**
 * 解析原始表头定义，生成表头布局和列定义
 */
export function parseHeaders(headers: Header[]): { headerLayout: HeaderCell[][], columns: FinalColumn[] } {
  // Compute the maximum depth of the header tree
  const getMaxDepth = (headers: Header[]): number => {
    let maxDepth = 0;
    for (const header of headers) {
      if (header.children && header.children.length > 0) {
        const depth = getMaxDepth(header.children) + 1;
        maxDepth = Math.max(maxDepth, depth);
      } else {
        maxDepth = Math.max(maxDepth, 1);
      }
    }
    return maxDepth;
  };

  // Compute the number of leaf nodes under a header
  const getLeafCount = (header: Header): number => {
    if (!header.children || header.children.length === 0) {
      return 1;
    }
    let count = 0;
    for (const child of header.children) {
      count += getLeafCount(child);
    }
    return count;
  };

  const maxDepth = getMaxDepth(headers);
  const headerLayout: HeaderCell[][] = Array.from({ length: maxDepth }, () => []);
  const columns: FinalColumn[] = [];

  // Process headers recursively to build headerLayout and columns
  const processHeaders = (headers: Header[], depth: number, startColIndex: number): number => {
    let colIndex = startColIndex;
    for (const header of headers) {
      if (!header.children || header.children.length === 0) {
        // Leaf node: spans from current depth to the bottom
        const cell: HeaderCell = {
          label: header.label,
          name: header.name,
          rowIndex: depth,
          colIndex: colIndex,
          rowSpan: maxDepth - depth,
          colSpan: 1,
        };
        headerLayout[depth].push(cell);
        // Add to columns (assuming leaf nodes have a name)
        columns.push({ label: header.label, name: header.name! });
        colIndex += 1;
      } else {
        // Non-leaf node: spans 1 row, colSpan is number of leaf descendants
        const leafCount = getLeafCount(header);
        const cell: HeaderCell = {
          label: header.label,
          rowIndex: depth,
          colIndex: colIndex,
          rowSpan: 1,
          colSpan: leafCount,
        };
        headerLayout[depth].push(cell);
        // Process children at the next depth
        processHeaders(header.children, depth + 1, colIndex);
        colIndex += leafCount;
      }
    }
    return colIndex;
  };

  processHeaders(headers, 0, 0);
  return { headerLayout, columns };
}
