.class Lcom/example/messagesmali/SourceSetAPI$1;
.super Landroid/os/Handler;
.source "SourceSetAPI.java"


# annotations
.annotation system Ldalvik/annotation/EnclosingMethod;
    value = Lcom/example/messagesmali/SourceSetAPI;->handlerSourceMethod()V
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x0
    name = null
.end annotation


# instance fields
.field final synthetic this$0:Lcom/example/messagesmali/SourceSetAPI;


# direct methods
.method constructor <init>(Lcom/example/messagesmali/SourceSetAPI;)V
    .locals 0
    .parameter

    .prologue
    .line 1
    iput-object p1, p0, Lcom/example/messagesmali/SourceSetAPI$1;->this$0:Lcom/example/messagesmali/SourceSetAPI;

    .line 165
    invoke-direct {p0}, Landroid/os/Handler;-><init>()V

    return-void
.end method


# virtual methods
.method public handleMessage(Landroid/os/Message;)V
    .locals 0
    .parameter "msg"

    .prologue
    .line 170
    invoke-super {p0, p1}, Landroid/os/Handler;->handleMessage(Landroid/os/Message;)V

    .line 171
    return-void
.end method
