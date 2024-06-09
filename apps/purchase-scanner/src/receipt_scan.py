def group_by_extension(files):
    ret = {}
    for f in files:
        ext = f[f.rfind(".") + 1:].lower()
        if ext in ret:
            ret[ext].append(f);
        else:
            ret[ext] = [f]

    return ret


def filter_receipts(scannedReceipts):
    # TODO: here if items are empty send rabbit message
    return [x for x in scannedReceipts if x['items']]
